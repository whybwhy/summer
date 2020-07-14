package com.summer.type;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum HttpStatus {

    HTTP_200(200, "OK"),
    HTTP_403(403, "Forbidden"),
    HTTP_404(404, "Not Found"),
    HTTP_500(500, "Interner Server Error"),
    HTTP_503(503, "Service Unavailable")
    ;

    private int httpCode;
    private String httpMessage;
}
