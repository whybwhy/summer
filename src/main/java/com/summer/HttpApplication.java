package com.summer;

import com.summer.nio.SimpleClient;
import com.summer.nio.SimpleClientMapper;
import com.summer.nio.SimpleServer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Slf4j
public class HttpApplication {

    private static final String line = "------------------------------------------------------------------------------------------------------------------------";

    public static void main(String[] args) throws Exception {


        Process server = SimpleServer.start();
        Thread.sleep(3000); // server start 대기 시간 확보
        SimpleClient client = SimpleClient.start();

        System.out.println(line);
        // GET 200 OK
        String responseGet = new SimpleClientMapper(client)
                .method("GET")
                .url("http://localhost:5050/read/notice").send();
        log.info(responseGet);
        System.out.println(line);

        // POST OK
        Map<String, String> body = new HashMap<>();
        body.put("KEY", "VALUE");
        String responsePost = new SimpleClientMapper(client)
                .method("POST")
                .body(body)
                .url("http://localhost:5050/read/notice").send();

        // 403 BAD REQUEST
        body.put("KEY", "VALUE");
        String response403 = new SimpleClientMapper(client)
                .method("POST")
                .body(body)
                .url("http://localhost:5000/test/test.exe").send();

        log.info(response403);
        System.out.println(line);

        SimpleClient.stop();
        server.destroy();


    }
}
