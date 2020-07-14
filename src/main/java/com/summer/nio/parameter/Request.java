package com.summer.nio.parameter;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.util.Map;

@Builder
@Accessors
@Getter
@Setter
@ToString
public class Request {
    private String method;
    private String url;
    private String version;
    private Map<String, String> header;
    private Map<String, String> body;
}
