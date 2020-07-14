package com.summer.nio.parameter;

import com.summer.type.HttpStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class ResponseHeader {

    private String version;
    private HttpStatus status;
    private Map<String, String> header;

    public ResponseHeader(String version, Map<String, String> header) {
        this.version = version;
        this.header = header;
    }

    public static ResponseHeader of(String version, Map<String, String> header) {
        return new ResponseHeader(version, header);
    }

    public static ResponseHeader of(String version, HttpStatus status, Map header) {
        return new ResponseHeader(version, status, header);
    }

    public String toString() {

        StringBuilder headerlist = new StringBuilder();

        header.forEach(
                (key, value) -> {
                    headerlist.append(key).append(": ").append(value).append("\n");
                }
        );

        return new StringBuilder(version).append(" ")
                .append(status.getHttpCode()).append(" ")
                .append(status.getHttpMessage()).append("\n")
                .append(headerlist.toString()).toString();
    }
}
