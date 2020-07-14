package com.summer.util;

import com.summer.config.FileLoader;
import com.summer.type.HttpStatus;

import java.text.MessageFormat;

public class ResponseGenerate {

    private static final String FORMAT_ERROR = FileLoader.load("RESPONSE_ERROR.json");

    public static String fail(HttpStatus httpStatus) {
        return FORMAT_ERROR.replace("\"%SERVER_CODE%\"", String.valueOf(httpStatus.getHttpCode()))
                .replace("%SERVER_MSG%", String.valueOf(httpStatus.getHttpMessage()));
    }
}
