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
package com.paiondata.common.constant;

/**
 * MessageConstant类提供了一组常量，用于定义在处理文件和目录操作时可能遇到的错误信息.
 * 这些常量有助于在应用中保持错误信息的一致性和可维护性。
 */
public class MessageConstant {

    /**
     * 错误信息常量，表示提供的路径不是一个有效的目录或该目录不存在.
     */
    public static final String DIRECTORY_ERROR = "不为目录或目录不存在：";

    /**
     * 错误信息常量，表示指定的文件不存在.
     */
    public static final String FILE_NOT_EXIST = "文件不存在：";

    /**
     * 错误信息常量，表示文件删除操作失败.
     */
    public static final String FILE_DELETE_ERROR = "无法删除文件：";

    /**
     * 错误信息常量，表示输入参数有误.
     */
    public static final String INPUT_ERROR = "输入参数有误";

    /**
     * 表示移除文件操作.
     */
    public static final String REMOVE_FILE = "移除文件: ";

    /**
     * 表示添加文件操作.
     */
    public static final String ADD_FILE = "新增文件: ";

    /**
     * 表示更新文件操作.
     */
    public static final String UPDATE_FILE = "更新文件: ";

    public static final String TRANSLATE_ERROR = "执行翻译时出现异常";
}
