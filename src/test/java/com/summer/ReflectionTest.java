package com.summer;

import com.summer.annotation.JUnitDocument;
import com.summer.parser.ServiceParser;
import com.summer.service.ReadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Arrays;

@Slf4j
@JUnitDocument("리플렉션 테스트")
public class ReflectionTest {

    @Test
    public void single() throws Exception {
        String url = "http://localhost:5050/read/notice";
        String url1 = "http://localhost:5050/read/date";
        String url2 = "http://localhost:5050/read/none";

        log.info("{}", Arrays.asList(url.split("/")));
        /*log.info("{}", new ServiceParser().getClazzInfo(url));
        log.info("{}", new ServiceParser().getMethodInfo(url));
        log.info("{}", new ServiceParser().reflectService(url));
        log.info("{}", new ServiceParser().reflectService0(url));
        log.info("{}", new ServiceParser().reflectService0(url1));
        log.info("{}", new ServiceParser().reflectService0(url2));
*/
    }

    @Test
    @JUnitDocument("어노테이션 테스트")
    public void dual() throws Exception {

    }

    @Test
    public void triple() {

    }

}
