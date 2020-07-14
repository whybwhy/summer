package com.summer;

import com.summer.annotation.JUnitDocument;
import com.summer.nio.parameter.Request;
import com.summer.parser.RequestParser;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class RequestParserTest {

    @JUnitDocument("헤더 파싱 테스트")
    @Test
    public void single() throws Exception {

        // given
        String protocol = "GET http://www.naver.com HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "BODY";

        // when
        Request header = new RequestParser().parse(protocol);
        log.info("HEADER {}", header);

    }

    @JUnitDocument("헤더 파싱 테스트")
    @Test
    public void dual() throws Exception {

        // given
        String protocol = "GET http://www.naver.com HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n";

        // when
        Request header = new RequestParser().parse(protocol);
        log.info("HEADER {}", header);

    }

    @JUnitDocument("헤더 파싱 테스트")
    @Test
    public void triple() throws Exception {

        // given
        String protocol = "GET http://www.naver.com HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" ;

        // when
        Request header = new RequestParser().parse(protocol);
        log.info("HEADER {}", header);
    }

    @JUnitDocument("헤더 파싱 테스트")
    @Test
    public void qudra() throws Exception {

        // given
        String protocol = "GET http://www.naver.com?a=1&b=2 HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" ;

        // when
        Request header = new RequestParser().parse(protocol);
        log.info("HEADER {}", header);
    }

    @JUnitDocument("헤더 파싱 테스트")
    @Test
    public void penta() throws Exception {

        // given
        String protocol = "GET http://www.naver.com? HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" ;

        // when
        Request header = new RequestParser().parse(protocol);
        log.info("HEADER {}", header);
    }

    @Test
    public void test() throws Exception {
        // given
        String protocol = "GET http://localhost:5000/read/noticelist?a=1 HTTP/1.1\n" +
                "Accept: application/json\n" +
                "User-Agent: local\n" +
                "\n" +
                "BODY";

        // when
        Request header = new RequestParser().parse(protocol);

        // then
        log.info("HEADER {}", header);
    }
}

