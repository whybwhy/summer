package com.summer.parser;

import com.summer.annotation.HttpMethods;
import com.summer.annotation.JUnitDocument;

import com.summer.service.ReadService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

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

        String url = "http://localhost:5050/read/noticelist";
        /*log.info("{}", new ServiceParser().reflectService("GET", url, String.class));
        log.info("{}", new ServiceParser().reflectService("POST", url, String.class));
*/

       /* String url2 = "http://localhost:5050/read/noneAnnotation";
        log.info("{}", new ServiceParser().reflectService("GET", url2));
        log.info("{}", new ServiceParser().reflectService("POST", url2));

        Class clazz = ReadService.class;
        System.out.println(clazz.getMethod("noneAnnotation").getReturnType().getName());
        System.out.println(clazz.getMethod("noneAnnotation").getReturnType() == Void.TYPE);

*/
        String url2 = "http://localhost:5050/read/none";
        /*log.info("{}", new ServiceParser().reflectService("GET", url2, String.class));
        log.info("{}", new ServiceParser().reflectService("POST", url2, String.class));



*/
    }

    @Test
    public void triple() {

    }

}
