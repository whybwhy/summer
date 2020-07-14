package com.summer;

import com.summer.nio.SimpleClient;
import com.summer.nio.SimpleServer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class EchoTest {
    public static void main(String[] args) throws Exception {
        Process server = SimpleServer.start();
        Thread.sleep(2000);
        SimpleClient client = SimpleClient.start();


        String protocol = "GET http://localhost:5000/read/noticelist2 HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "BODY";


        String protocol2 = "GET http://localhost:5000/read/none HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "BODY";


        String protocol3 = "GET http://localhost:5000/read/noticelist?a=1 HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "BODY";

        String protocol4 = "PUT http://localhost:5000/ HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "BODY";

        String protocol5 = "PUT http://localhost:5000/read/notice/list/data HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "BODY";

        String protocol6 = "GET http://localhost:5000/read/notice?a=123&b=1111 HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "BODY";

        String protocol7 = "POST http://localhost:5000/read/notice?a=123&b=1111 HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" ;

        String protocol8 = "POST http://localhost:5000/read/notice?a=123&b=1111 HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "{ \"a\" : 1}";

        String protocol9 = "GET http://localhost:5000/read/none HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "{ \"a\" : 1}";

        String protocol10 = "POST http://localhost:5000/read/date HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "{ \"a\" : 1}";

        String protocol11 = "GET http://localhost:5000/read/echo?a=123 HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "{ \"a\" : 1}";
        //String response0 = client.sendMessageToServer("TEST");
        //String response1 = client.sendMessageToServer(protocol);
        //String response2 = client.sendMessageToServer(protocol2);
        //String response3 = client.sendMessageToServer(protocol3);
        String response4 = client.sendProtocolToServer(protocol5);
        String response3 = client.sendProtocolToServer(protocol3);
        String response6 = client.sendProtocolToServer(protocol6);
        String response8 = client.sendProtocolToServer(protocol8);
        String response9 = client.sendProtocolToServer(protocol9);
        String response10 = client.sendProtocolToServer(protocol10);
        String response11 = client.sendProtocolToServer(protocol11);

        log.info("@" + response4);
        log.info("@" + response3);
        log.info("@" + response6);
        log.info("@" + response8);
        log.info("@" + response9);
        log.info("@" + response10);
        log.info("@" + response11);
        //System.out.println(response6);


        SimpleClient.stop();
        server.destroy();
    }
}
