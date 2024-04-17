package com.paiondata;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.entity.AliyunInfo;
import org.junit.jupiter.api.Test;
import com.paiondata.entity.SparkInfo;

public class CreateClientTest {

    static String apiKey = "sk-**********************";
    static String inputPath = "docs";
    @Test
    public void testAliyun() throws NoApiKeyException, InputRequiredException {
        // Act
        AliyunInfo info = AliyunInfo.builder()
                .apiKey(apiKey)
                .inputPath(inputPath)
                .mode(false)
                .build();
        CreateClient.aliTranslate(info);
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
        CreateClient.aliTranslate(info);
    }

    String appid = "9ef2faca";
    String apiSecret = "YmU3MGYyYTMxODM4ODFlNmEyOWEwNDk1";
    String apiKey1 = "5a863ea65f88a876c03c4e7f389afa60";

    @Test
    public void testSpark() throws NoApiKeyException, InputRequiredException {
        // Act
        SparkInfo info = SparkInfo.builder()
                .appid(appid)
                .apiSecret(apiSecret)
                .apiKey(apiKey1)
                .inputPath(inputPath)
                .build();
        CreateClient.sparkTranslate(info);
    }

    static String inputPath2 = "docs2";

    @Test
    public void testSparkAddOutputPath() throws NoApiKeyException, InputRequiredException {
        // Act
        SparkInfo info = SparkInfo.builder()
                .appid(appid)
                .apiSecret(apiSecret)
                .apiKey(apiKey1)
                .inputPath(inputPath2)
                .build();
        CreateClient.sparkTranslate(info);
    }
}