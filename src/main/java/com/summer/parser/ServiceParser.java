package com.summer.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.summer.annotation.HttpMethods;
import com.summer.exception.HttpException;
import com.summer.nio.parameter.Request;
import com.summer.service.CreateService;
import com.summer.service.DeleteService;
import com.summer.service.ReadService;
import com.summer.service.UpdateService;
import com.summer.type.HttpStatus;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;
import java.util.stream.Stream;

@Slf4j
public class ServiceParser<T> implements Parsable<Request, T> {

    private static Map<String, Class> serviceMap;
    private final int RULE_URL_FORMAT = 5;
    static {
        serviceMap = new HashMap<>();
        serviceMap.put("read", ReadService.class);
        serviceMap.put("create", CreateService.class);
        serviceMap.put("update", UpdateService.class);
        serviceMap.put("delete", DeleteService.class);
    }

    private List<String> parse(String url) throws ArrayIndexOutOfBoundsException {
        return Arrays.asList(url.split("/"));
    }

    private String getClazzInfo(String url) throws ArrayIndexOutOfBoundsException {
        return this.parse(url).get(3);
    }

    private String getMethodInfo(String url) throws ArrayIndexOutOfBoundsException {
        return this.parse(url).get(4);
    }

    @Override
    public T parse(Request request) throws Exception {

        // 요청된 URI로 부터 서비스 검색
        if (parse(request.getUrl()).size() != RULE_URL_FORMAT)
            throw new HttpException(HttpStatus.HTTP_403, "허용하지 않은 서비스 접근");

        Class clazz = serviceMap.get(getClazzInfo(request.getUrl()));
        Optional.ofNullable(clazz).orElseThrow(() -> new HttpException(HttpStatus.HTTP_403, "허용하지 않은 서비스 접근"));

        Method method = Stream.of(clazz.getDeclaredMethods())
                .filter(m -> getMethodInfo(request.getUrl()).equalsIgnoreCase(m.getName()))
                .findFirst().orElseThrow(() -> new HttpException(HttpStatus.HTTP_404, "존재하지 않는 Service 요청"));

        // @HttpMethods 설정 확인
        if (!method.isAnnotationPresent(HttpMethods.class))
            throw new HttpException(HttpStatus.HTTP_500, "Service에 @HttpMethods를 설정하십시오.");

        // 허용하는 HttpMethod 와 요청된 Http 일치 여부
        HttpMethods methods = method.getDeclaredAnnotation(HttpMethods.class);
        HttpMethods.MethodType[] acceptedList = methods.accept();
        for (HttpMethods.MethodType methodType : acceptedList) {

            if (methodType.name().equalsIgnoreCase(request.getMethod())) {

                if(method.getParameterTypes().length > 0) {
                    return (T) new ObjectMapper().writeValueAsString(method.invoke(clazz.newInstance(), request.getBody()));   // reflect
                }

                return (T) new ObjectMapper().writeValueAsString(method.invoke(clazz.newInstance()));   // reflect
            }
        }


        throw new HttpException(HttpStatus.HTTP_403, "허용하지 않은 서비스 접근");

    }
}
