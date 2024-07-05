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
 * DirectoryException 类继承自BaseException，专门用于表示与目录操作相关的异常情况.
 *
 * @constructor DirectoryException 初始化构造函数，接收一个字符串参数msg，详细描述目录操作中遇到的错误情况。
 *
 * @description 该类作为BaseException的子类，进一步细化异常类型，使得在处理涉及目录的业务逻辑时，
 *               能够更精确地捕获和识别目录相关的问题，如路径不存在、权限不足等错误，便于进行针对性的错误处理和调试。
 */
public class DirectoryException extends BaseException {
    /**
     * 构造函数，接收一个字符串参数msg，详细描述目录操作中遇到的错误情况.
     * @param msg 错误信息
     */
    public DirectoryException(final String msg) {
        super(msg);
    }
}
