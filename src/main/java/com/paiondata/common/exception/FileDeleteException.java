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
 * FileDeleteException 类是BaseException的子类，专用于处理文件删除操作过程中遇到的异常情形.
 *
 * @constructor FileDeleteException 初始化构造函数，接受一个字符串参数msg，描述文件删除失败的具体原因，如文件不存在、权限不足等。
 *
 * @description 该类的目的是为了更精确地抛出和处理与文件删除操作相关的错误，相较于通用的BaseException，
 *               它能更清晰地指示出是文件删除这一特定操作失败，有助于开发人员迅速识别问题并采取相应的错误处理措施。
 */
public class FileDeleteException extends BaseException {
    /**
     * 构造函数，初始化FileDeleteException对象，并传入一个字符串参数msg，描述文件删除失败的具体原因.
     * @param msg 文件删除失败的原因
     */
    public FileDeleteException(final String msg) {
        super(msg);
    }
}
