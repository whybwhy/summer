package com.summer.nio;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class SimpleClientMapper {
    private String method;
    private String url;
    private String version;
    private String header;
    private String body;
    private SimpleClient client;

    public SimpleClientMapper(SimpleClient client) {
        this.version = "HTTP/1.1";
        this.header = "Accept: application/json\n" +
                "User-Agent: local";
        this.client = client;
    }


    public SimpleClientMapper method(String method) {
        this.method = method;
        return this;
    }

    public SimpleClientMapper url(String url) {
        this.url = url;
        return this;
    }

    public SimpleClientMapper body(Map<String, String> body) {

        try {
            this.body = new ObjectMapper().writeValueAsString(body);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return this;
    }

    public String send() {

        String protocol = method+" " +url+ " " + version+ "\n"
                +header+ "\n";

        if (this.body != null)
            protocol = protocol + "\n" + this.body;

        return client.sendProtocolToServer(protocol);

    }
}
