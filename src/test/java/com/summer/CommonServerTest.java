package com.summer;

import com.summer.annotation.JUnitDocument;
import com.summer.nio.SimpleClientMapper;
import com.summer.nio.SimpleClient;
import com.summer.nio.SimpleServer;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@JUnitDocument("Server Test")
public class CommonServerTest {

    Process server = null;
    SimpleClient client = null;

    @Before
    public void before() throws Exception {
        server = SimpleServer.start();
        Thread.sleep(2000);
        client = SimpleClient.start();
    }

    @After
    public void after() throws Exception {
        SimpleClient.stop();
        server.destroy();
    }

    @JUnitDocument("GET Method Test")
    @Test
    public void single() {

        // given
        Arrays.asList("http://localhost:5000/read/date"
                    ,"http://localhost:5000/read/noticelist"
                    ,"http://localhost:5000/read/notice?id=19485?a=1"
                    , "http://localhost:5000/read/echo?id=19485")
                .stream()
                .forEach(url -> {
                    String response = new SimpleClientMapper(client)  // when
                            .method("GET")
                            .url(url).send();
                    log.info("@RESPONSE : {}", response);   // then
                });
    }

    @JUnitDocument("POST Method Test")
    @Test
    public void dual() throws Exception {

        // given
        Map<String, String> body = new HashMap();
        body.put("id", "23819");
        body.put("name", "보람.윤");

        Arrays.asList("http://localhost:5000/read/date"
                ,"http://localhost:5000/read/noticelist"
                ,"http://localhost:5000/read/notice"
                , "http://localhost:5000/read/echo")
                .stream()
                .forEach(url -> {

                        String response = new SimpleClientMapper(client)
                                .method("POST")
                                .body(body)
                                .url(url).send();   // when

                        log.info("@RESPONSE : {}", response );  //then
                });
    }

    @JUnitDocument("404 Not Found Test")
    @Test
    public void triple() {

        // given
        Arrays.asList("http://localhost:5000/read/bill")
                .stream()
                .forEach(url -> {
                    String response = new SimpleClientMapper(client)
                            .method("GET")
                            .url(url).send();   // when
                    log.info("@RESPONSE : {}", response);   // then
                });
    }

    @JUnitDocument("403 Bad Request Test")
    @Test
    public void quad() throws Exception {
        // given
        Arrays.asList("http://localhost:8000/../../../../etc/passwd"
                ,"http://localhost:5000/test/test.exe"
                ,"http://localhost:5000/"
                , "http://localhost"
                , "http://localhost:5000/read/none")
                .stream()
                .forEach(url -> {
                    String response = new SimpleClientMapper(client)
                            .method("GET")
                            .url(url).send();   // when
                    log.info("@RESPONSE : {}", response);   // then
                });

    }

    @JUnitDocument("Request가 없을 경우의 Selector 의 동작 확인 용도")
    @Test
    public void penta() throws Exception {
        // 1. ServerSocket open
        // 2. SelectionKey.OP_ACCEPT Key 로 진입. 하지만 READ 권한은 얻지 못함
        // 3. Acceptable 처리 후

        Thread.sleep(5000); // 4. selector.select(); 에서 대기

        // 5. ServerSocket Process Stop Read 권한 획득 후 isReadable 처리 중 종료
    }

}
