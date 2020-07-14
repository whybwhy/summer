package com.summer.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

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

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {

            FileChannel channel = FileChannel.open(Paths.get(ServerConfigLoader.class.getResource("/application.json").getPath()), StandardOpenOption.READ);
            channel.read(buffer);
            channel.close();

            ObjectMapper mapper = new ObjectMapper();
            serverConfig = mapper.readValue(buffer.array(), ServerConfig.class);

        } catch (Exception e) {
            log.error("서비스 환경설정 초기화에 실패 했습니다.", e);
        }
    }
}
