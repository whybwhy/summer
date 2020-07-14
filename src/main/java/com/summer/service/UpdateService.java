package com.summer.service;

import com.summer.annotation.HttpMethods;

public class UpdateService {

    @HttpMethods(accept = {HttpMethods.MethodType.PUT})
    public boolean update() {
        return Boolean.FALSE;
    }
}
