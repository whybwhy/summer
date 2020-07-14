package com.summer.service;

import com.summer.annotation.HttpMethods;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Service
 *
 * RULE : request 는 Map 으로
 * VOID 타입의 메소드는 만들지 말것
 * @HttpMethods 어노테이션을 추가 (default : GET)
 */
@Slf4j
public class ReadService {

    @HttpMethods(accept = {HttpMethods.MethodType.GET})
    public List noticeList() {
        return Arrays.asList("서울시 지방세", "6월 BC 카드 청구서", "6월 삼천리 가스요금 청구서", "6월 아파트 관리비", "6월 학원비");
    }

    @HttpMethods(accept = {HttpMethods.MethodType.GET, HttpMethods.MethodType.POST})
    public Map<String, String> notice(Map<String, String> parameter) {

        log.info("@Parameter : ", parameter);
        Map<String, String> result = new LinkedHashMap<>();
        result.put("성명", "윤보람");
        result.put("카드소유주", "윤보람");
        result.put("납부타입", "자동이체-카드");
        result.put("세목명", "재산세(주택1기분)");
        result.put("납기일", "2020.07.30");
        result.put("납부금액", "80만원");

        return result;
    }

    @HttpMethods(accept = {HttpMethods.MethodType.GET, HttpMethods.MethodType.POST})
    public String date() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    @HttpMethods
    public void none() {

    }

    @HttpMethods(accept = {HttpMethods.MethodType.GET, HttpMethods.MethodType.POST})
    public Map<String, String> echo(Map<String, String> parameter) {
        return parameter;
    }

    public void noneAnnotation() {

    }
}
