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
package com.paiondata.common.entity;

import lombok.Builder;

/**
 * AliyunInfo类封装了与阿里云服务交互所需的基本信息，继承自BaseEntity，使用Lombok的@Builder注解增强构建对象的能力1。
 *
 * @field service 静态常量，表示服务提供商名称，默认为"Aliyun"。
 * @field apiKey  阿里云API密钥，用于认证和授权。
 * @field targetLanguage 目标语言代码，指定翻译或其他语言处理服务的目标语言。
 * @field mode  功能模式开关，布尔值，用于控制不同的服务模式或行为。
 *
 * @constructor AliyunInfo 默认构造函数。
 * @constructor AliyunInfo 初始化构造函数，接收apiKey、targetLanguage和mode作为参数。
 *
 * @method getApiKey 获取阿里云API密钥。
 * @method setApiKey 设置阿里云API密钥。
 * @method getTargetLanguage 获取目标语言代码。
 * @method setTargetLanguage 设置目标语言代码。
 * @method getMode 获取功能模式开关。
 * @method setMode 设置功能模式开关。
 */
@Builder
public class AliyunInfo extends BaseEntity{

    public static final String service = "Aliyun";

    private String apiKey;

    private String targetLanguage;

    private Boolean mode;

    /**
     * 无参构造函数
     */
    public AliyunInfo() {
    }

    /**
     * 构造函数
     * @param apiKey 阿里云API密钥
     * @param targetLanguage 获取目标语言代码
     * @param mode 功能模式开关
     */
    public AliyunInfo(String apiKey, String targetLanguage, Boolean mode) {
        this.apiKey = apiKey;
        this.targetLanguage = targetLanguage;
        this.mode = mode;
    }

    /**
     * 获取阿里云API密钥
     * @return 阿里云API密钥
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * 设置阿里云API密钥
     * @param apiKey 阿里云API密钥
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * 获取目标语言代码
     * @return 目标语言代码
     */
    public String getTargetLanguage() {
        return targetLanguage;
    }

    /**
     * 设置目标语言代码
     * @param targetLanguage 目标语言代码
     */
    public void setTargetLanguage(String targetLanguage) {
        this.targetLanguage = targetLanguage;
    }

    /**
     * 获取功能模式开关
     * @return 功能模式开关
     */
    public Boolean getMode() {
        return mode;
    }

    /**
     * 设置功能模式开关
     * @param mode 功能模式开关
     */
    public void setMode(Boolean mode) {
        this.mode = mode;
    }
}
