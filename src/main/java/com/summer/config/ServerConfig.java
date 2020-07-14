package com.summer.config;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ServerConfig {
    private String host;
    private int port;
    private String charset;

}
