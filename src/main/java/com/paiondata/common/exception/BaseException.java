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
 * BaseException 类是继承自RuntimeException的自定义异常类，用于作为项目中所有业务异常的基类.
 *
 * @constructor BaseException 默认构造函数，创建一个没有详细信息的异常实例。
 * @constructor BaseException 初始化构造函数，接收一个字符串参数msg，用于设置异常的具体信息。
 *
 * @description 本类设计旨在提供一个统一的异常处理入口，使得在处理业务逻辑时抛出的异常更加规范且易于管理。
 *               开发者可以通过扩展此类来定义特定业务场景下的异常类型，利用其构造函数传递详细的错误信息。
 */
public class BaseException extends RuntimeException {
    /**
     * 默认构造函数，创建一个没有详细信息的异常实例.
     */
    public BaseException() {
    }

    /**
     * 初始化构造函数，接收一个字符串参数msg，用于设置异常的具体信息.
     * @param msg 异常信息
     */
    public BaseException(final String msg) {
        super(msg);
    }
}
