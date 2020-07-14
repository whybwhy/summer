package com.summer.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;


@Slf4j
public class FileLoader {
    public static String load(String file) {

        String stream = new String();

        try {
            stream = IOUtils.toString(ServerConfigLoader.class.getResourceAsStream(file), "UTF-8");

        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

        return stream;
    }

}
