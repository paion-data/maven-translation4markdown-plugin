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
package com.paiondata.common.exception;

/**
 * TranslationException 类继承自BaseException，用于表示翻译过程中遇到的异常情况.
 *
 * @constructor TranslationException 初始化构造函数，接受一个字符串参数msg，描述翻译操作中发生的错误信息，例如服务不可用、请求超时或翻译错误等。
 *
 * @description 该类的目的是为了区分并明确指出在执行文本翻译功能时遇到的问题，相比于基类BaseException，
 *               它提供了更具体的上下文信息，有助于开发者快速定位翻译模块的故障，及时采取相应的错误处理策略或进行调试。
 */
public class TranslationException extends BaseException {
    /**
     * 构造函数.
     * @param msg 错误信息
     */
    public TranslationException(final String msg) {
        super(msg);
    }
}
