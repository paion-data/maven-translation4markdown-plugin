package com.paiondata;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.entity.AliyunInfo;
import com.paiondata.util.MarkdownHandler;
import com.paiondata.Exception.TranslationException;
import com.paiondata.entity.SparkInfo;

import org.apache.maven.plugin.MojoExecutionException;

import java.util.List;

public class CreateClient {
    public static final String DEFAULT_OUTPUT_PATH = "i18n/zh-cn/docusaurus-plugin-content-docs/current/";

    // 阿里云大模型
    private static void aliTranslateSingle(AliyunInfo info, String outputPath, String inputPath) throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getApiKey() == null) {
            throw new TranslationException("输入参数有误");
        }
        MarkdownHandler.translate(AliyunInfo.service, info.getApiKey(), inputPath, outputPath, info.getMode().toString());
    }

    public static void aliTranslate(AliyunInfo info, List<String> files) throws NoApiKeyException, InputRequiredException {
        for (String file : files) {
            aliTranslateSingle(info, DEFAULT_OUTPUT_PATH, file);
        }
    }

    // 星火大模型
    private static void sparkTranslateSingle(SparkInfo info, String outputPath, String inputPath) throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getAppid() == null || info.getApiKey() == null || info.getApiSecret() == null) {
            throw new TranslationException("输入参数有误");
        }
        MarkdownHandler.translate(SparkInfo.service, info.getAppid(), info.getApiKey(), info.getApiSecret(), inputPath, outputPath);
    }

    public static void sparkTranslate(SparkInfo info, List<String> files) throws NoApiKeyException, InputRequiredException {
        for (String file : files) {
            sparkTranslateSingle(info, DEFAULT_OUTPUT_PATH, file);
        }
    }
}
