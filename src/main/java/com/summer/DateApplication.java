package com.summer;

import com.summer.nio.SimpleClientMapper;
import com.summer.nio.SimpleClient;
import com.summer.nio.SimpleServer;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class DateApplication {

    public static void main(String[] args) throws Exception {

        Process server = SimpleServer.start();
        Thread.sleep(2000); // server start 대기 시간 확보
        SimpleClient client = SimpleClient.start();

        String response = new SimpleClientMapper(client)
                .method("GET")
                .url("http://localhost:5050/read/date")
                .send();

        log.info("DATE : {}", response);


        SimpleClient.stop();
        server.destroy();

    }
}
