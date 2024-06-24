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
            return FileHandler.readMarkdownFile(inputPath);
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
            FileHandler.createMarkdownFile(outputFile, trans);
            log.info("创建翻译文件：" + outputFile);
        } catch (IOException e) {
            throw new TranslationException("创建文件失败" + e.getMessage());
        }
    }
}
