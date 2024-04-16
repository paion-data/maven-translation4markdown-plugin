package com.paiondata;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.entity.AliyunInfo;
import com.paiondata.util.MarkdownHandler;

import com.paiondata.Exception.TranslationException;
import com.paiondata.entity.SparkInfo;

public class CreateClient {
    public static final String DEFAULT_OUTPUT_PATH = "i18n/zh-cn/docusaurus-plugin-content-docs/current/";

    // 处理输出路径
    private static String handleOutputPath(String path) {
        if (path == null) {
            return DEFAULT_OUTPUT_PATH;
        } else {
            return path.endsWith("/") ? path : path + "/";
        }
    }

    // 阿里云大模型
    public static String aliTranslate(AliyunInfo info) throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getApiKey() == null || info.getInputPath() == null) {
            throw new TranslationException("输入参数有误");
        }

        String outputPath = handleOutputPath(info.getOutputPath());
        return MarkdownHandler.translate(AliyunInfo.service, info.getApiKey(), info.getInputPath(), outputPath, info.getMode().toString());
    }

    // 星火大模型
    public static String sparkTranslate(SparkInfo info) throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getAppid() == null || info.getApiKey() == null || info.getApiSecret() == null || info.getInputPath() == null) {
            throw new TranslationException("输入参数有误");
        }

        String outputPath = handleOutputPath(info.getOutputPath());
        return MarkdownHandler.translate(SparkInfo.service, info.getAppid(), info.getApiKey(), info.getApiSecret(), info.getInputPath(), outputPath);
    }

}
