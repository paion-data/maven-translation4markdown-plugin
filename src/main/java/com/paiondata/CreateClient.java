package com.paiondata;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.entity.AliyunInfo;
import com.paiondata.util.MarkdownFileReader;
import com.paiondata.util.MarkdownHandler;

import com.paiondata.Exception.TranslationException;
import com.paiondata.entity.SparkInfo;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class CreateClient {
    public static final String DEFAULT_OUTPUT_PATH = "i18n/zh-cn/docusaurus-plugin-content-docs/current/";
    public static final String DEFAULT_INPUT_PATH = "docs";

    // 阿里云大模型
    private static void aliTranslateSingle(AliyunInfo info, String outputPath, String inputPath) throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getApiKey() == null || info.getInputPath() == null) {
            throw new TranslationException("输入参数有误");
        }
        MarkdownHandler.translate(AliyunInfo.service, info.getApiKey(), inputPath, outputPath, info.getMode().toString());
    }

    public static void aliTranslate(AliyunInfo info) throws NoApiKeyException, InputRequiredException {
        List<String> markdownFiles = MarkdownFileReader.getAllMarkdownFiles(DEFAULT_INPUT_PATH);
        for (String file : markdownFiles) {
            aliTranslateSingle(info, DEFAULT_OUTPUT_PATH, file);
        }
    }

    // 星火大模型
    private static void sparkTranslateSingle(SparkInfo info, String outputPath, String inputPath) throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getAppid() == null || info.getApiKey() == null || info.getApiSecret() == null || info.getInputPath() == null) {
            throw new TranslationException("输入参数有误");
        }
        MarkdownHandler.translate(SparkInfo.service, info.getAppid(), info.getApiKey(), info.getApiSecret(), inputPath, outputPath);
    }

    public static void sparkTranslate(SparkInfo info) throws NoApiKeyException, InputRequiredException {
        List<String> markdownFiles = MarkdownFileReader.getAllMarkdownFiles(DEFAULT_INPUT_PATH);
        for (String file : markdownFiles) {
            sparkTranslateSingle(info, DEFAULT_OUTPUT_PATH, file);
        }
    }

}
