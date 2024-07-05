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
package com.paiondata.common.util;

import com.paiondata.common.entity.MarkdownFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 文件处理器类，提供Markdown文件的读取和创建操作.
 */
public class FileHandler {

    /**
     * 从指定路径读取Markdown文件内容.
     *
     * @param filePath Markdown文件的路径。
     *
     * @return MarkdownFile 包含文件名和文件内容的MarkdownFile对象。
     *
     * @throws IOException 如果文件不存在、不是常规文件、内容为空或读取时发生其他I/O错误。
     */
    public static MarkdownFile readMarkdownFile(final String filePath) throws IOException {
        final Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new FileNotFoundException("指定的文件不存在或不是一个常规文件：" + filePath);
        }

        final String content = Files.readString(path, StandardCharsets.UTF_8).trim(); // 使用Java 11的Files.readString简化读取

        if (content.isEmpty()) {
            throw new IOException("输入文件内容为空");
        }

        return new MarkdownFile(path.getFileName().toString(), content);
    }

    /**
     * 在指定路径创建一个新的Markdown文件并写入内容.
     * <p>
     * 如果文件的父目录不存在，将自动创建。
     *
     * @param filePath 要创建的Markdown文件的完整路径。
     * @param content 要写入文件的内容。
     *
     * @throws IOException 如果创建文件、写入内容时发生I/O错误。
     */
    public static void createMarkdownFile(final String filePath, final String content) throws IOException {
        final Path file = Paths.get(filePath);

        // 自动创建缺失的目录结构
        Files.createDirectories(file.getParent());

        // 确保写入流在操作完成后关闭
        try (OutputStreamWriter writer = new OutputStreamWriter(
                Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE),
                StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}
