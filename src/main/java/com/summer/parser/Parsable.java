package com.summer.parser;

public interface Parsable<T1, T2> {
    T2 parse(T1 t1) throws Exception;
}
