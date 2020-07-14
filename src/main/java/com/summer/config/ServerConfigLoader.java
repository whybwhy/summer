package com.summer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class ServerConfigLoader {

    private static ServerConfig serverConfig;

    public static ServerConfig getInstance() {

        if (serverConfig == null)
            load();

        return serverConfig;
    }

    private static void load() {
        try {

            ObjectMapper mapper = new ObjectMapper();
            serverConfig = mapper.readValue(IOUtils.toString(ServerConfigLoader.class.getResourceAsStream("/application.json"), "UTF-8"), ServerConfig.class);

        } catch (Exception e) {
            log.error("서비스 환경설정 초기화에 실패 했습니다.", e);
        }
    }
}
