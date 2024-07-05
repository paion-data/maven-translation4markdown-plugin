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

import static com.paiondata.TranslationMojo.DEFAULT_INPUT_PATH;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.paiondata.common.entity.MarkdownFile;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * FileHandlerTest 类是一系列针对文件操作功能的单元测试集合，特别是针对Markdown文件的读写操作.
 * 这些测试确保了`FileHandler`类中的方法能够正确处理文件读取、创建以及处理特殊情况，如空文件或不存在的目录.
 */
public class FileHandlerTest {

    /**
     * 定义测试使用的Markdown文件内容.
     */
    private static final String TEST_CONTENT = "This is a test markdown file created by the unit test.";

    /**
     * 使用JUnit的@TempDir注解来自动创建一个临时目录，在测试结束后自动清理.
     */
    @TempDir
    Path tempDir;

    /**
     * 测试读取Markdown文件的功能.
     * 验证读取的MarkdownFile对象非空且文件名正确.
     *
     * @throws IOException 输入输出异常。
     */
    @Test
    public void readMarkdownFileTest() throws IOException {
        final MarkdownFile markdownFile = FileHandler.readMarkdownFile(DEFAULT_INPUT_PATH + "/example.md");

        assertNotNull(markdownFile, "MarkdownFile object should not be null");
        assertEquals("example.md", markdownFile.getFileName(), "File name should match the expected value");
    }

    /**
     * 测试尝试读取一个空Markdown文件的情况，预期应抛出IOException.
     */
    @Test
    public void readEmptyMarkdownFileTest() {
        final String emptyFilePath = "src/test/resources/empty_markdown_file.md";

        assertThrows(IOException.class, () -> FileHandler.readMarkdownFile(emptyFilePath),
                "An IOException should be thrown when reading an empty file");
    }

    /**
     * 测试Markdown文件的创建功能.
     * 验证文件创建后存在且内容与预期相符.
     *
     * @throws IOException 输入输出异常。
     */
    @Test
    public void createMarkdownFileTest() throws IOException {
        final String directory = tempDir.toString();
        final File file = new File(directory, "test01.md");

        FileHandler.createMarkdownFile(file.getPath(), TEST_CONTENT);

        assertTrue(Files.exists(Paths.get(file.getPath())), "The markdown file should exist after creation");

        final String actualContent = Files.readString(Paths.get(file.getPath()), StandardCharsets.UTF_8);
        assertEquals(TEST_CONTENT, actualContent, "The content of the created file should match the provided content");
    }

    /**
     * 测试在不存在的目录下创建Markdown文件的功能.
     * 预期应当能成功创建文件而不抛出异常，并且文件应存在于预期路径.
     */
    @Test
    public void createMarkdownFileWithNonExistentDirectoryTest() {
        final String nonExistentDirectoryPath = "target/test_output/non_existent_dir/create_markdown_file_test.md";
        final String testContent = "This is a test markdown file with a non-existent directory.";

        assertDoesNotThrow(() -> FileHandler.createMarkdownFile(nonExistentDirectoryPath, testContent),
                "Creating a markdown file in a non-existent directory should succeed without throwing an exception");

        assertTrue(Files.exists(Paths.get(nonExistentDirectoryPath)),
                "The markdown file in the non-existent directory should exist after creation");
    }
}
