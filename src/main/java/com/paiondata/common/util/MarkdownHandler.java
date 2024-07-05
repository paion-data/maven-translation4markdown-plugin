/*
 * Copyright 2024 Paion Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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

/**
 * MarkdownHandler 类提供了Markdown文件的翻译处理功能。
 * 该类利用外部翻译服务（如阿里云、Spark等）对Markdown文件内容进行翻译，
 * 并将翻译后的内容保存为新的Markdown文件。
 * <p>
 * 使用了SLF4J进行日志记录，以便跟踪操作过程中的关键事件。
 */
@Slf4j
public class MarkdownHandler {

    /**
     * 主要翻译处理方法，根据指定的服务调用相应的翻译API。
     *
     * @param service 翻译服务的名称，决定调用哪个翻译服务的API。
     * @param params 参数数组，包括但不限于输入文件路径、输出目录路径、API密钥等。
     *
     * @throws TranslationException 如果发生翻译相关的错误。
     * @throws NoApiKeyException 如果未提供API密钥。
     * @throws InputRequiredException 如果缺少必要的输入参数。
     */
    public static void translate(String service, String... params)
            throws TranslationException, NoApiKeyException, InputRequiredException {
        String trans;
        MarkdownFile file = readFile(params[0]);
        String detectLanguage = LanguageDetector.detectLanguage(file.getContent());
        String inputContent = "请将Markdown文本翻译成" + detectLanguage +
                "，请注意不要翻译Markdown文本中出现的一些格式关键字信息和超链接:\n" + file.getContent();

        switch (service) {
            case AliyunInfo.SERVICE -> trans =
                    AliDashScopeAI.callWithMessage(params[2], Boolean.parseBoolean(params[3]), inputContent);
            case SparkInfo.SERVICE -> trans =
                    SparkAI.getAnswer(params[2], params[3], params[4], inputContent);
            default -> throw new TranslationException("不支持的翻译服务：" + service);
        }

        createOutputFile(params[1], file.getFileName(), trans);
    }

    /**
     * 读取指定路径的Markdown文件。
     *
     * @param inputPath Markdown文件的路径。
     *
     * @return MarkdownFile 包含文件名和内容的MarkdownFile对象。
     *
     * @throws TranslationException 如果读取文件时发生错误。
     */
    private static MarkdownFile readFile(String inputPath) throws TranslationException {
        try {
            return FileHandler.readMarkdownFile(inputPath);
        } catch (IOException e) {
            throw new TranslationException("读取md文件失败" + e.getMessage());
        }
    }

    /**
     * 根据提供的路径、文件名和翻译内容创建一个新的Markdown文件。
     *
     * @param outputPath 目标文件的输出目录路径。
     * @param fileName 新文件的名称。
     * @param trans 翻译后的内容。
     *
     * @throws TranslationException 如果翻译结果为空或文件创建失败。
     */
    private static void createOutputFile(String outputPath, String fileName, String trans)
            throws TranslationException {
        if (trans == null) {
            throw new TranslationException("翻译结果为空");
        }

        String outputFile = outputPath + File.separator + fileName;
        try {
            FileHandler.createMarkdownFile(outputFile, trans);
            log.info("创建翻译文件：" + outputFile);
        } catch (IOException e) {
            throw new TranslationException("创建文件失败" + e.getMessage());
        }
    }
}
