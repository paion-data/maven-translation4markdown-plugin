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
 * SparkInfo类封装了与Spark服务交互所需的认证和配置信息，继承自BaseEntity。
 *
 * @field service 静态常量，表示服务提供商名称，默认为"Spark"。
 * @field appid Spark服务的应用ID，用于唯一标识应用程序。
 * @field apiSecret Spark服务的API密钥，用于安全认证。
 * @field apiKey Spark服务的API密钥，与apiSecret一起用于认证（注意：根据实际情况，可能只有一个秘钥有效）。
 *
 * @constructor SparkInfo 默认构造函数。
 * @constructor SparkInfo 初始化构造函数，接收appid、apiSecret和apiKey作为参数。
 *
 * @method getAppid 获取Spark服务的应用ID。
 * @method setAppid 设置Spark服务的应用ID。
 * @method getApiSecret 获取Spark服务的API密钥。
 * @method setApiSecret 设置Spark服务的API密钥。
 * @method getApiKey 获取Spark服务的另一个API密钥（或相同的密钥依据服务设定）。
 * @method setApiKey 设置Spark服务的另一个API密钥。
 */
@Builder
public class SparkInfo extends BaseEntity {

    public static final String service = "Spark";

    private String appid;

    private String apiSecret;

    private String apiKey;

    /**
     * 默认构造函数
     */
    public SparkInfo() {
    }

    /**
     * 构造函数
     * @param appid Spark服务的应用ID
     * @param apiSecret Spark服务的API密钥
     * @param apiKey Spark服务的API密钥
     */
    public SparkInfo(String appid, String apiSecret, String apiKey) {
        this.appid = appid;
        this.apiSecret = apiSecret;
        this.apiKey = apiKey;
    }

    /**
     * 获取Spark服务的应用ID
     * @return 应用ID
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置Spark服务的应用ID
     * @param appid 应用ID
     */
    public void setAppid(String appid) {
        this.appid = appid;
    }

    /**
     * 获取Spark服务的API密钥
     * @return API密钥
     */
    public String getApiSecret() {
        return apiSecret;
    }

    /**
     * 设置Spark服务的API密钥
     * @param apiSecret API密钥
     */
    public void setApiSecret(String apiSecret) {
        this.apiSecret = apiSecret;
    }

    /**
     * 获取Spark服务的另一个API密钥（或相同的密钥依据服务设定）
     * @return API密钥
     */
    public String getApiKey() {
        return apiKey;
    }

    /**
     * 设置Spark服务的另一个API密钥（或相同的密钥依据服务设定）
     * @param apiKey API密钥
     */
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
}
