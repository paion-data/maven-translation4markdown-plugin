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
package com.paiondata;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.common.entity.AliyunInfo;
import com.paiondata.common.util.MarkdownHandler;
import com.paiondata.common.exception.TranslationException;
import com.paiondata.common.entity.SparkInfo;

import java.util.List;

public class CreateClient {

    // 阿里云大模型
    private static void aliTranslateSingle(AliyunInfo info, String outputPath, String inputPath)
            throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getApiKey() == null) {
            throw new TranslationException("输入参数有误");
        }
        MarkdownHandler.translate(AliyunInfo.service, inputPath, outputPath,
                info.getApiKey(), info.getMode().toString());
    }

    public static void aliTranslate(AliyunInfo info, List<String> files)
            throws NoApiKeyException, InputRequiredException {
        for (String file : files) {
            aliTranslateSingle(info, TranslationMojo.DEFAULT_OUTPUT_PATH, file);
        }
    }

    // 星火大模型
    private static void sparkTranslateSingle(SparkInfo info, String outputPath, String inputPath)
            throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getAppid() == null || info.getApiKey() == null || info.getApiSecret() == null) {
            throw new TranslationException("输入参数有误");
        }
        MarkdownHandler.translate(SparkInfo.service, inputPath, outputPath,
                info.getAppid(), info.getApiKey(), info.getApiSecret());
    }

    public static void sparkTranslate(SparkInfo info, List<String> files)
            throws NoApiKeyException, InputRequiredException {
        for (String file : files) {
            sparkTranslateSingle(info, TranslationMojo.DEFAULT_OUTPUT_PATH, file);
        }
    }
}
