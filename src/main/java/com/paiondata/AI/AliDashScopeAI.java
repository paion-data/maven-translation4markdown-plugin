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

public class AliDashScopeAI {

    public static String callWithMessage(String apiKey, String content, Boolean mode)
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
