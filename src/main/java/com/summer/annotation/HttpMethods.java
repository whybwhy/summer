package com.summer.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface HttpMethods {

    enum MethodType {GET, POST, DELETE, PUT}
    MethodType[] accept() default MethodType.GET;

}
