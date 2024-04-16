package com.paiondata;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.entity.AliyunInfo;
import org.junit.jupiter.api.Test;
import com.paiondata.entity.SparkInfo;

public class CreateClientTest {

    static String apiKey = "sk-**********************";
    static String inputPath = "docs/example.md";
    @Test
    public void testAliyun() throws NoApiKeyException, InputRequiredException {
        // Act
        AliyunInfo info = AliyunInfo.builder()
                .apiKey(apiKey)
                .inputPath(inputPath)
                .mode(false)
                .build();
        String result = CreateClient.aliTranslate(info);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testAliyunAddOutputPath() throws NoApiKeyException, InputRequiredException {
        // Act
        AliyunInfo info = AliyunInfo.builder()
                .apiKey(apiKey)
                .inputPath(inputPath)
                .outputPath("i18n/test/")
                .mode(true)
                .build();
        String result = CreateClient.aliTranslate(info);

        // Assert
        assertNotNull(result);
    }

    String appid = "**************************";
    String apiSecret = "**************************";
    String apiKey1 = "**************************";

    @Test
    public void testSpark() throws NoApiKeyException, InputRequiredException {
        // Act
        SparkInfo info = SparkInfo.builder()
                .appid(appid)
                .apiSecret(apiSecret)
                .apiKey(apiKey1)
                .inputPath(inputPath)
                .build();
        String result = CreateClient.sparkTranslate(info);

        // Assert
        assertNotNull(result);
    }

    @Test
    public void testSparkAddOutputPath() throws NoApiKeyException, InputRequiredException {
        // Act
        SparkInfo info = SparkInfo.builder()
                .appid(appid)
                .apiSecret(apiSecret)
                .apiKey(apiKey1)
                .inputPath(inputPath)
                .outputPath("i18n/test")
                .build();
        String result = CreateClient.sparkTranslate(info);

        // Assert
        assertNotNull(result);
    }
}