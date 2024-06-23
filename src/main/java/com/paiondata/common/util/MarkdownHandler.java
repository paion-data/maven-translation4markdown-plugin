package com.paiondata.common.util;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.AI.AliDashScopeAI;
import com.paiondata.AI.SparkAI;
import com.paiondata.common.exception.TranslationException;
import com.paiondata.common.entity.AliyunInfo;
import com.paiondata.common.entity.MarkdownFile;
import com.paiondata.common.entity.SparkInfo;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

@Slf4j
public class MarkdownHandler {

    public static void translate(String service, String... params)
            throws TranslationException, NoApiKeyException, InputRequiredException {
        String trans;
        MarkdownFile file = readFile(params[0]);
        String detectLanguage = LanguageDetector.detectLanguage(file.getContent());
        String inputContent = file.getContent() + "\n\n请帮我翻译成" + detectLanguage;

        switch (service) {
            case AliyunInfo.service -> trans =
                    AliDashScopeAI.callWithMessage(params[2], Boolean.parseBoolean(params[3]), inputContent);
            case SparkInfo.service -> trans =
                    SparkAI.getAnswer(params[2], params[3], params[4], inputContent);
            default -> throw new TranslationException("不支持的翻译服务：" + service);
        }

        createOutputFile(params[1], file.getFileName(), trans);
    }

    private static MarkdownFile readFile(String inputPath) throws TranslationException {
        try {
            return IOHandler.readMarkdownFile(inputPath);
        } catch (IOException e) {
            throw new TranslationException("读取md文件失败" + e.getMessage());
        }
    }

    private static void createOutputFile(String outputPath, String fileName, String trans)
            throws TranslationException {
        if (trans == null) {
            throw new TranslationException("翻译结果为空");
        }

        String outputFile = outputPath + File.separator + fileName;
        try {
            IOHandler.createMarkdownFile(outputFile, trans);
            log.info("创建翻译文件：" + outputFile);
        } catch (IOException e) {
            throw new TranslationException("创建文件失败" + e.getMessage());
        }
    }
}
