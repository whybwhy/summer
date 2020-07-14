package com.summer.nio;

import com.summer.config.ServerConfig;
import com.summer.config.ServerConfigLoader;
import com.summer.exception.HttpException;
import com.summer.nio.parameter.Request;
import com.summer.nio.parameter.Response;
import com.summer.nio.parameter.ResponseHeader;
import com.summer.parser.RequestParser;
import com.summer.parser.ServiceParser;
import com.summer.type.HttpStatus;
import com.summer.util.ResponseGenerate;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;

@Slf4j
public class EchoServer {

    public static void main(String[] args) throws Exception {

        // ServerConfig
        ServerConfig serverConfig = ServerConfigLoader.getInstance();
        if (serverConfig == null)
            throw new Exception();

        // Charset
        Charset charset = Charset.forName(serverConfig.getCharset());

        // Selector
        Selector selector = Selector.open();

        // ServerSocketChannel
        ServerSocketChannel server = ServerSocketChannel.open();
        server.bind(new InetSocketAddress(serverConfig.getHost(), serverConfig.getPort()));
        server.configureBlocking(false);    // NonBlocking
        server.register(selector, SelectionKey.OP_ACCEPT);

        while(true) {

            selector.select();

            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();

                if (key.isAcceptable()) {
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    client.register(selector, SelectionKey.OP_READ); // Read
                }

                if (key.isReadable()) {

                    // Request
                    ByteBuffer buffer = ByteBuffer.allocate(1024*1024);

                    SocketChannel client = (SocketChannel) key.channel();
                    client.read(buffer);
                    buffer.flip();

                    // Request 분석
                    Request request = new RequestParser().parse(new String(buffer.array()));
                    ResponseHeader responseHeader = ResponseHeader.of(request.getVersion(), request.getHeader());

                    // Response 구성
                    Response response;
                    String responseBody;

                    // Response 셋업
                    try {

                        responseBody  = (String) new ServiceParser().parse(request);   // Service 구성
                        responseHeader.setStatus(HttpStatus.HTTP_200);

                    } catch (HttpException httpException) {
                        log.info(httpException.getMessage(), httpException);

                        responseHeader.setStatus(httpException.getStatus());
                        responseBody = ResponseGenerate.fail(httpException.getStatus());
                    } catch (Exception e) {
                        log.info(e.getMessage(), e);

                        responseHeader.setStatus(HttpStatus.HTTP_500);
                        responseBody = ResponseGenerate.fail(HttpStatus.HTTP_500);
                    }

                    response = Response.of(responseHeader, responseBody);
                    client.write(charset.encode(response.toString()));

                }

                iterator.remove();
            }
        }
    }

    public static Process start() throws IOException, InterruptedException {
        String javaHome = System.getProperty("java.home");
        String javaBin = javaHome + File.separator + "bin" + File.separator + "java";
        String classpath = System.getProperty("java.class.path");
        String className = EchoServer.class.getCanonicalName();

        ProcessBuilder builder = new ProcessBuilder(javaBin, "-cp", classpath, className);

        return builder.start();
    }
}
