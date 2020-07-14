package com.summer.parser;

import com.summer.annotation.JUnitDocument;
import com.summer.config.FileLoader;
import com.summer.config.ServerConfigLoader;
import com.summer.type.HttpStatus;
import com.summer.util.ResponseGenerate;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.Assert.*;

import java.lang.annotation.Documented;

@Slf4j
public class FileLoaderTest {

    @JUnitDocument("파일 Read 테스트 - 기대결과 : 정상")
    @Test
    public void single() throws Exception {

        // given & when
        String stream = FileLoader.load("RESPONSE_ERROR.json");

        // then
        Assert.assertNotNull(stream);

    }

    @JUnitDocument("파일 Read 테스트 - 기대결과 : Exception ")
    @Test
    public void dual() throws Exception {

        // given & when
        String stream = FileLoader.load("RESPONSE_ERROR");

        // then
        Assert.assertNotNull(stream);

    }
}
