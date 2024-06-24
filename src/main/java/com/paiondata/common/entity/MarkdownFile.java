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

/**
 * MarkdownFile 类代表了一个Markdown文件实体，继承自BaseEntity，用于存储和操作Markdown文件的基本信息。
 *
 * @field fileName Markdown文件的名称，包括路径和扩展名，用于标识文件。
 * @field content Markdown文件的纯文本内容，包含Markdown格式的文本和元数据。
 *
 * @constructor MarkdownFile 全参数构造器，接收文件名和文件内容初始化MarkdownFile实例。
 *
 * @method getFileName 获取Markdown文件的名称。
 * @method getContent 获取Markdown文件的内容。
 */
public class MarkdownFile extends BaseEntity {
    private String fileName;
    private String content;

    /**
     * 构造方法，用于创建MarkdownFile实例。
     *
     * @param fileName Markdown文件的名称。
     * @param content Markdown文件的内容。
     */
    public MarkdownFile(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    /**
     * 获取Markdown文件的名称。
     *
     * @return 返回文件名称。
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * 获取Markdown文件的内容。
     *
     * @return 返回文件内容。
     */
    public String getContent() {
        return content;
    }
}
