package com.summer.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class EchoClient {

    private static SocketChannel client;
    private static ByteBuffer buffer;
    private static ByteBuffer bufferResponse;
    private static EchoClient instance;

    private EchoClient() {
        try {
            client = SocketChannel.open(new InetSocketAddress("localhost", 5000));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static EchoClient start() {
        return instance == null ? new EchoClient() : instance;
    }

    public static void stop() throws Exception {
        client.close();
        buffer.clear();
        buffer = null;
    }

    public String sendProtocolToServer(String protocol) {
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
