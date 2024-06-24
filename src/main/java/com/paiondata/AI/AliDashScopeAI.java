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
package com.paiondata.AI;

import com.alibaba.dashscope.aigc.generation.Generation;
import com.alibaba.dashscope.aigc.generation.GenerationResult;
import com.alibaba.dashscope.aigc.generation.models.QwenParam;
import com.alibaba.dashscope.common.Message;
import com.alibaba.dashscope.common.MessageManager;
import com.alibaba.dashscope.common.Role;
import com.alibaba.dashscope.exception.ApiException;
import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;

/**
 * AliDashScopeAI 类提供了生成特定消息回复的功能。
 */
public class AliDashScopeAI {

    /**
     * 调用AliDashScopeAI服务以生成消息回复。
     *
     * @param apiKey   API密钥，用于认证请求。
     * @param mode     是否启用高级模式。如果为true，则使用QWEN_MAX模型；否则使用QWEN_TURBO模型。
     * @param content  用户提供的输入内容，作为生成回复的基础。
     *
     * @return 生成的回复消息内容。
     *
     * @throws NoApiKeyException      如果未提供API密钥。
     * @throws ApiException           在调用API过程中发生的一般错误。
     * @throws InputRequiredException 如果输入内容为空或不符合要求。
     */
    public static String callWithMessage(String apiKey, Boolean mode, String content)
            throws NoApiKeyException, ApiException, InputRequiredException {
        Generation gen = new Generation();
        MessageManager msgManager = new MessageManager(10);
        Message systemMsg =
                Message.builder().role(Role.SYSTEM.getValue()).content("You are a helpful assistant.").build();
        Message userMsg = Message.builder().role(Role.USER.getValue()).content(content).build();
        msgManager.add(systemMsg);
        msgManager.add(userMsg);
        String useMode = Generation.Models.QWEN_TURBO;
        if (mode) {
            useMode = Generation.Models.QWEN_MAX;
        }
        QwenParam param =
                QwenParam.builder().model(useMode).messages(msgManager.get())
                        .apiKey(apiKey)
                        .resultFormat(QwenParam.ResultFormat.MESSAGE)
                        .build();
        GenerationResult result = gen.call(param);
        return result.getOutput().getChoices().get(0).getMessage().getContent();
    }
}
