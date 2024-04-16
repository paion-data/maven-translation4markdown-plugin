package com.paiondata;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.entity.AliyunInfo;
import com.paiondata.util.MarkdownHandler;

import com.paiondata.Exception.TranslationException;
import com.paiondata.entity.SparkInfo;

public class CreateClient {
    public static final String DEFAULT_OUTPUT_PATH = "i18n/translate-md/current/";

    public static final String EN = "英语";

    public static final String CHS = "中文";

    // 处理输出路径
    private static String handleOutputPath(String path) {
        if (path == null) {
            return DEFAULT_OUTPUT_PATH;
        } else {
            return path.endsWith("/") ? path : path + "/";
        }
    }

    // 阿里云大模型 英语
    public static String aliTranslate2EN(AliyunInfo info) throws TranslationException, NoApiKeyException, InputRequiredException {
        return operate1(info, EN);
    }

    // 阿里云大模型 中文
    public static String aliTranslate2CHS(AliyunInfo info) throws TranslationException, NoApiKeyException, InputRequiredException {
        return operate1(info, CHS);
    }

    private static String operate1(AliyunInfo info, String lang) throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getApiKey() == null || info.getInputPath() == null) {
            throw new TranslationException("输入参数有误");
        }

        String outputPath = handleOutputPath(info.getOutputPath());
        return MarkdownHandler.translate(AliyunInfo.service, info.getApiKey(), info.getInputPath(), outputPath, lang, info.getMode().toString());
    }

    // 星火大模型 英语
    public static String sparkTranslate2EN(SparkInfo info) throws TranslationException, NoApiKeyException, InputRequiredException {
        return operate2(info, EN);
    }

    // 星火大模型 中文
    public static String sparkTranslate2CHS(SparkInfo info) throws TranslationException, NoApiKeyException, InputRequiredException {
        return operate2(info, CHS);
    }

    private static String operate2(SparkInfo info, String lang) throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getAppid() == null || info.getApiKey() == null || info.getApiSecret() == null || info.getInputPath() == null) {
            throw new TranslationException("输入参数有误");
        }

        String outputPath = handleOutputPath(info.getOutputPath());
        return MarkdownHandler.translate(SparkInfo.service, info.getAppid(), info.getApiKey(), info.getApiSecret(), info.getInputPath(), outputPath, lang);
    }
}
