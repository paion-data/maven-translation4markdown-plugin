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
import com.paiondata.common.constant.MessageConstant;
import com.paiondata.common.entity.AliyunInfo;
import com.paiondata.common.util.MarkdownHandler;
import com.paiondata.common.exception.TranslationException;
import com.paiondata.common.entity.SparkInfo;

import java.util.List;

/**
 * CreateClient 类提供了使用不同大模型服务（阿里云、星火）进行Markdown文件翻译的功能.
 * 该类封装了针对不同服务的翻译调用逻辑，支持批量处理文件翻译任务.
 */
public class CreateClient {

    /**
     * 单个文件翻译方法，专门用于阿里云大模型服务的翻译请求.
     *
     * @param info 阿里云服务的相关配置信息.
     * @param outputPath 翻译后文件的输出目录路径.
     * @param inputPath 待翻译的Markdown文件路径.
     *
     * @throws NoApiKeyException 如果缺少API密钥等必要参数.
     * @throws InputRequiredException 如果输入参数有误或不完整.
     * @throws TranslationException 如果翻译过程中出现错误.
     */
    private static void aliTranslateSingle(final AliyunInfo info, final String outputPath, final String inputPath)
            throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getApiKey() == null) {
            throw new TranslationException(MessageConstant.INPUT_ERROR);
        }
        MarkdownHandler.translate(AliyunInfo.SERVICE, inputPath, outputPath,
                info.getApiKey());
    }

    /**
     * 批量翻译文件，调用单个文件翻译方法处理列表中的每个文件.
     * 默认输出目录为 {@link TranslationMojo#DEFAULT_OUTPUT_PATH}.
     *
     * @param info 阿里云服务的配置信息.
     * @param files 待翻译Markdown文件的路径列表.
     *
     * @throws NoApiKeyException 如果缺少API密钥等必要参数.
     * @throws InputRequiredException 如果输入参数有误或不完整.
     */
    public static void aliTranslate(final AliyunInfo info, final List<String> files)
            throws NoApiKeyException, InputRequiredException {
        for (final String file : files) {
            aliTranslateSingle(info, TranslationMojo.DEFAULT_OUTPUT_PATH, file);
        }
    }

    /**
     * 单个文件翻译方法，专用于星火大模型服务的翻译请求.
     *
     * @param info 星火服务的相关配置信息.
     * @param outputPath 翻译后文件的输出目录路径.
     * @param inputPath 待翻译的Markdown文件路径.
     *
     * @throws NoApiKeyException 如果缺少appid、apiKey或apiSecret等必要参数.
     * @throws InputRequiredException 如果输入参数有误或不完整.
     * @throws TranslationException 如果翻译过程中出现错误.
     */
    private static void sparkTranslateSingle(final SparkInfo info, final String outputPath, final String inputPath)
            throws NoApiKeyException, InputRequiredException {
        if (info == null || info.getAppid() == null || info.getApiKey() == null || info.getApiSecret() == null
                || info.getEngine() == null) {
            throw new TranslationException(MessageConstant.INPUT_ERROR);
        }
        MarkdownHandler.translate(SparkInfo.SERVICE, inputPath, outputPath,
                info.getAppid(), info.getApiKey(), info.getApiSecret(), info.getEngine());
    }

    /**
     * 批量翻译文件，调用单个文件翻译方法处理列表中的每个文件.
     * 默认输出目录为 {@link TranslationMojo#DEFAULT_OUTPUT_PATH}.
     *
     * @param info 星火服务的配置信息.
     * @param files 待翻译Markdown文件的路径列表.
     *
     * @throws NoApiKeyException 如果缺少appid、apiKey或apiSecret等必要参数.
     * @throws InputRequiredException 如果输入参数有误或不完整.
     */
    public static void sparkTranslate(final SparkInfo info, final List<String> files)
            throws NoApiKeyException, InputRequiredException {
        for (final String file : files) {
            sparkTranslateSingle(info, TranslationMojo.DEFAULT_OUTPUT_PATH, file);
        }
    }
}
