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
 * AliyunInfo类封装了与阿里云服务交互所需的基本信息，继承自BaseEntity，使用Lombok的@Builder注解增强构建对象的能力.
 *
 * @field service 静态常量，表示服务提供商名称，默认为"Aliyun"。
 * @field apiKey  阿里云API密钥，用于认证和授权。
 *
 * @constructor AliyunInfo 默认构造函数。
 * @constructor AliyunInfo 初始化构造函数，接收apiKey、targetLanguage和mode作为参数。
 *
 * @method getApiKey 获取阿里云API密钥。
 * @method setApiKey 设置阿里云API密钥。
 */
@Builder
public class AliyunInfo extends BaseEntity {

    /**
     * 服务提供名称.
     */
    public static final String SERVICE = "Aliyun";

    private String apiKey;

    /**
     * 无参构造函数.
     */
    public AliyunInfo() {
    }

    /**
     * 构造函数.
     * @param apiKey 阿里云API密钥
     */
    public AliyunInfo(final String apiKey) {
        this.apiKey = apiKey;
    }

    /**
     * 获取阿里云API密钥.
     * @return 阿里云API密钥
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * 设置阿里云API密钥.
     * @param apiKey 阿里云API密钥
     */
    public void setApiKey(final String apiKey) {
        this.apiKey = apiKey;
    }
}
