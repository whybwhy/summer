package com.summer.exception;

import com.summer.type.HttpStatus;
import lombok.Getter;

@Getter
public class HttpException extends Exception {

    private HttpStatus status;

    public HttpException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

}
