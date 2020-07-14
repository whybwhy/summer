package com.summer.nio.parameter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Builder
@Accessors
@AllArgsConstructor
public class Response {
    private ResponseHeader header;
    private String body;

    public String toString() {
        return header.toString().concat("\n").concat(body);
    }


    public static Response of(ResponseHeader header, String body) {

        return new Response(header, body);
    }

}
