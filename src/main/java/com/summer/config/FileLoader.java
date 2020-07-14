package com.summer.config;

import lombok.extern.slf4j.Slf4j;

import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

@Slf4j
public class FileLoader {
    public static String load(String file) {

        ByteBuffer buffer = ByteBuffer.allocate(1024);

        try {

            FileChannel channel = FileChannel.open(Paths.get(ServerConfigLoader.class.getResource("/".concat(file)).getPath()), StandardOpenOption.READ);
            channel.read(buffer);
            buffer.flip();
            channel.close();

        } catch (Exception e) {
            log.error("{} 초기화 실패", file, e);
        }

        return Charset.forName("UTF-8").decode(buffer).toString();

    }

}
