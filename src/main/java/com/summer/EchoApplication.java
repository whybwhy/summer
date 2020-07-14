package com.summer;

import com.summer.nio.SimpleClient;
import com.summer.nio.SimpleClientMapper;
import com.summer.nio.SimpleServer;
import lombok.extern.slf4j.Slf4j;

import java.util.Scanner;

@Slf4j
public class EchoApplication {

    public static void main(String[] args) throws Exception {

        Scanner scanner = new Scanner(System.in);
        Process server = SimpleServer.start();
        Thread.sleep(3000); // server start 대기 시간 확보
        SimpleClient client = SimpleClient.start();

        while(true) {
            System.out.println("에코 테스트입니다. 텍스트를 입력해주세요 (종료를 원하시면 EXIT를 입력해주세요)");
            String inputStream = scanner.next();

            if (inputStream.equalsIgnoreCase("EXIT")) {
                SimpleClient.stop();
                server.destroy();
                break;
            }

            String response = new SimpleClientMapper(client)
                    .method("GET")
                    .url("http://localhost:5050/read/echo?key=".concat(inputStream))
                    .send();

            log.info(response);
        }
    }
}
