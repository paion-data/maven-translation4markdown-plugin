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

public class IOHandlerTest {
    @Test
    public void readMarkdownFileTest() throws IOException {
        MarkdownFile markdownFile = FileHandler.readMarkdownFile(DEFAULT_INPUT_PATH + "/example.md");

        assertNotNull(markdownFile, "MarkdownFile object should not be null");
        assertEquals("example.md", markdownFile.getFileName(), "File name should match the expected value");
    }

    @Test
    public void readEmptyMarkdownFileTest() {
        String emptyFilePath = "src/test/resources/empty_markdown_file.md";

        assertThrows(IOException.class, () -> FileHandler.readMarkdownFile(emptyFilePath),
                "An IOException should be thrown when reading an empty file");
    }

    // 临时路径
    @TempDir
    Path tempDir;

    private static final String TEST_CONTENT = "This is a test markdown file created by the unit test.";

    @Test
    public void createMarkdownFileTest() throws IOException {
        String directory = tempDir.toString();
        File file = new File(directory, "test01.md");

        FileHandler.createMarkdownFile(file.getPath(), TEST_CONTENT);

        assertTrue(Files.exists(Paths.get(file.getPath())), "The markdown file should exist after creation");

        String actualContent = Files.readString(Paths.get(file.getPath()), StandardCharsets.UTF_8);
        assertEquals(TEST_CONTENT, actualContent, "The content of the created file should match the provided content");
    }

    @Test
    public void createMarkdownFileWithNonExistentDirectoryTest() {
        String nonExistentDirectoryPath = "target/test_output/non_existent_dir/create_markdown_file_test.md";
        String testContent = "This is a test markdown file with a non-existent directory.";

        assertDoesNotThrow(() -> FileHandler.createMarkdownFile(nonExistentDirectoryPath, testContent),
                "Creating a markdown file in a non-existent directory should succeed without throwing an exception");

        assertTrue(Files.exists(Paths.get(nonExistentDirectoryPath)),
                "The markdown file in the non-existent directory should exist after creation");
    }
}
