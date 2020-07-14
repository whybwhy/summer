package com.summer.parser;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.summer.nio.parameter.Request;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

@Slf4j
public class RequestParser implements Parsable<String, Request> {

    public static final String GET = "GET";
    public static final String POST = "POST";
    public static final String COLON = ":";
    public static final String EQUAL = "=";
    public static final String SPACE = " ";
    public static final String QUESTION = "?";
    public static final String AND = "&";
    public static final String PREFIX_BRACKET = "{";

    public Request parse(String protocol) throws Exception {

        Map<String, String> headers = new HashMap<>();
        Map<String, String> body = new HashMap<>();

        String[] protocolByline = protocol.split("\n");
        String[] headerLine0 = Stream.of(protocolByline).findFirst().get().split(SPACE);

        Stream.of(protocolByline)
                .skip(1)
                .filter(h -> !h.contains(PREFIX_BRACKET))
                .filter(h -> h.contains(COLON))
                .forEach(h -> headers.put(h.split(COLON)[0], h.split(COLON)[1].trim()));


        try {
            if (headerLine0[0].equalsIgnoreCase(GET)) {
                if (headerLine0[1].contains(QUESTION)) {
                    String[] kvs = headerLine0[1].substring(headerLine0[1].indexOf(QUESTION) + 1).split(AND);
                    for(String kv : kvs) {
                        body.put(kv.split(EQUAL)[0], kv.split(EQUAL)[1]);
                    }

                }
            }

            if (headerLine0[0].equalsIgnoreCase(POST)) {
                body = new ObjectMapper().readValue(protocol.substring(protocol.indexOf(PREFIX_BRACKET)), Map.class);
            }

        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage(), e);
        }

        return Request.builder()
                .method(headerLine0[0])
                .url(headerLine0[1].split("\\?")[0])
                .version(headerLine0[2])
                .header(headers)
                .body(body)
                .build();
    }
}
