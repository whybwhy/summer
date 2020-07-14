package com.summer.nio;

import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

@Slf4j
public class SimpleClient {

    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static ByteBuffer bufferResponse;
    private static SimpleClient instance;

    private SimpleClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 5000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static SimpleClient start() {
        return instance == null ? new SimpleClient() : instance;
    }

    public static void stop() throws Exception {
        client.close();
        buffer.clear();
        buffer = null;
    }

    public String sendProtocolToServer(String protocol) {

        log.debug(protocol);

        buffer = ByteBuffer.wrap(protocol.getBytes());
        bufferResponse = ByteBuffer.allocate(1024 * 1024);

        String response = null;
        try {
            client.write(buffer);
            client.read(bufferResponse);
            bufferResponse.flip();

            response = Charset.forName("UTF-8").decode(bufferResponse).toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            buffer.clear();
            bufferResponse.clear();
        }

        return response;
    }
}
