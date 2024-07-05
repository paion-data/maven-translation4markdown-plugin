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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.paiondata.TranslationMojo;
import com.paiondata.common.entity.FileResult;

import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * DirectoryUtilTest 类是一组单元测试，用于验证 {@link DirectoryUtil} 类中各个方法的正确性.
 * 包括文件哈希生成、当前目录文件列表获取、文件删除、以及文件同步逻辑等功能.
 * 测试覆盖了新增、更新、删除文件的场景，确保目录操作的准确性.
 */
@Slf4j
public class DirectoryUtilTest {

    // 定义多个临时测试路径
    private static final String TEMP_PATH = "src" + File.separator + "test" + File.separator + "java" + File.separator
            + "com" + File.separator + "paiondata" + File.separator + "%s";
    private static final String TEMP_PATH01 = String.format(TEMP_PATH, "testPath01");
    private static final String TEMP_PATH02 = String.format(TEMP_PATH, "testPath02");
    private static final String TEMP_PATH03 = String.format(TEMP_PATH, "testPath03");
    private static final String TEMP_PATH04 = String.format(TEMP_PATH, "testPath04");
    private static final String DOCS = "docs";
    private static final String TEST01_MD = "test01.md";
    private static final String TEST02_MD = "test02.md";
    private static final String TEST03_MD = "test03.md";
    private static final String KEY1 = "key1";
    private static final String KEY2 = "key2";
    private static final String KEY3 = "key3";
    private static final String VALUE1 = "value1";
    private static final String FILE_TXT = "file.txt";
    private static final String KEY1_VALUE1 = "key1:value1";
    private static final String KEY2_VALUE2 = "key2:value2";

    /**
     * 测试文件哈希生成方法.
     * 验证生成的哈希映射不为空.
     *
     * @throws IOException 输入输出异常。
     * @throws NoSuchAlgorithmException 无此类算法异常。
     */
    @Test
    public void generateFileHashTest() throws IOException, NoSuchAlgorithmException {
        final List<String> currentFileList = DirectoryUtil.getCurrentFileList(TranslationMojo.DEFAULT_INPUT_PATH);
        final Map<String, String> generatedHashes = DirectoryUtil.generateFileHash(currentFileList);
        assertNotNull(generatedHashes, "Generated hashes should not be null");
    }

    /**
     * 测试获取当前目录文件列表方法.
     * 验证返回列表非空且包含预期文件.
     */
    @Test
    public void testGetCurrentFileList() {
        final List<String> currentFileList = DirectoryUtil.getCurrentFileList(TranslationMojo.DEFAULT_INPUT_PATH);

        // 验证返回的文件列表非空
        assertNotNull(currentFileList);
        assertFalse(currentFileList.isEmpty(), "Expected non-empty file list");

        // 验证文件列表包含预期的文件名（根据实际测试需求添加）
        assertTrue(currentFileList.contains(DOCS + File.separator + "example.md"), "Expected file missing");
    }

    /**
     * 测试文件删除方法.
     * 根据给定文件列表删除指定目录下的文件，并验证删除结果.
     *
     * @throws IOException 输入输出异常。
     */
    @Test
    public void testDeletedFiles() throws IOException {
        final String directory = TEMP_PATH01;
        new File(directory, TEST01_MD).createNewFile();
        new File(directory, TEST02_MD).createNewFile();
        new File(directory, TEST03_MD).createNewFile();

        final List<String> fileList = new ArrayList<>();
        fileList.add(DOCS + File.separator + TEST01_MD);
        fileList.add(DOCS + File.separator + TEST02_MD);

        DirectoryUtil.deleteFile(fileList, directory);
        assertFalse(new File(directory, TEST01_MD).exists());
        assertFalse(new File(directory, TEST02_MD).exists());
        // test03.md没被删除
        assertTrue(new File(directory, TEST03_MD).exists());

        deletefilesindirectory(directory);
    }

    /**
     * 测试文件同步逻辑，专注于新增文件场景.
     * 验证同步后新增文件被正确创建且内容匹配.
     *
     * @throws Exception 异常捕捉。
     */
    @Test
    public void testSyncFileWithMapAddedKeys() throws Exception {
        final String directory = TEMP_PATH02;
        final Map<String, String> inputMap = new HashMap<>();
        inputMap.put(KEY1, VALUE1);
        inputMap.put(KEY2, "value2");

        final FileResult result = DirectoryUtil.syncFileWithMap(directory, inputMap);

        // 验证返回的 FileResult 不为 null
        assertNotNull(result);
        // 验证 FileResult 中的 addedKeys 是否包含了 inputMap 中的所有键，即新添加的键都被正确识别
        assertTrue(result.getAddedKeys().containsAll(inputMap.keySet()));
        // 验证 updatedKeys 和 deletedKeys 列表为空，因为在这个测试用例中我们没有模拟已有文件内容的更新或删除情况
        assertTrue(result.getUpdatedKeys().isEmpty());
        assertTrue(result.getDeletedKeys().isEmpty());

        // 检查文件是否已成功创建在指定目录下
        final File file = new File(directory, FILE_TXT);
        assertTrue(file.exists());
        // 通过 BufferedReader 读取新创建的文件内容，逐行验证其内容与 inputMap 中的数据一致
        final BufferedReader reader = new BufferedReader(new FileReader(file));
        assertEquals(KEY1_VALUE1, reader.readLine());
        assertEquals(KEY2_VALUE2, reader.readLine());
        assertNull(reader.readLine());
        reader.close();

        deletefilesindirectory(directory);
    }

    /**
     * 测试文件同步逻辑，专注于更新文件内容场景.
     * 验证同步后文件内容被正确更新，同时记录更新和删除的文件.
     *
     * @throws Exception 异常捕捉。
     */
    @Test
    public void testSyncFileWithMapUpdatedKeys() throws Exception {
        final String directory = TEMP_PATH03;
        final File file = new File(directory, FILE_TXT);

        // 初始化文件
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("key1:initialValue1");
        writer.newLine();
        writer.write("key2:initialValue2");
        writer.close();

        final Map<String, String> inputMap = new HashMap<>();
        inputMap.put(KEY1, "updatedValue1"); // 更新现有的key
        inputMap.put(KEY3, "value3"); // 添加一个key

        final FileResult result = DirectoryUtil.syncFileWithMap(directory, inputMap);

        assertNotNull(result);
        assertTrue(result.getAddedKeys().contains(KEY3));
        assertTrue(result.getUpdatedKeys().contains(KEY1));
        assertTrue(result.getDeletedKeys().contains(KEY2));
        assertTrue(file.exists());

        final BufferedReader reader = new BufferedReader(new FileReader(file));
        assertEquals("key1:updatedValue1", reader.readLine()); // 更新的值
        assertEquals("key3:value3", reader.readLine()); // 新增的值
        assertNull(reader.readLine());
        reader.close();

        deletefilesindirectory(directory);
    }

    /**
     * 测试文件同步逻辑，专注于删除文件场景.
     * 验证指定的文件从目录中被正确移除.
     *
     * @throws Exception 异常捕捉。
     */
    @Test
    public void testSyncFileWithMapDeletedKeys() throws Exception {
        final String directory = TEMP_PATH04;
        final File file = new File(directory, FILE_TXT);

        final BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(KEY1_VALUE1);
        writer.newLine();
        writer.write(KEY2_VALUE2);
        writer.close();

        final Map<String, String> inputMap = new HashMap<>();
        inputMap.put(KEY1, VALUE1); // 保持key1

        final FileResult result = DirectoryUtil.syncFileWithMap(directory, inputMap);

        assertNotNull(result);
        assertTrue(result.getUpdatedKeys().isEmpty());
        assertTrue(result.getDeletedKeys().contains(KEY2)); // key2应该被删除
        assertTrue(file.exists());

        final BufferedReader reader = new BufferedReader(new FileReader(file));
        assertEquals(KEY1_VALUE1, reader.readLine()); // 保持的的key还在
        assertNull(reader.readLine());
        reader.close();

        deletefilesindirectory(directory);
    }

    /**
     * 辅助方法，用于删除指定目录下的所有文件.
     *
     * @param directoryPath 目录路径。
     */
    private static void deletefilesindirectory(final String directoryPath) {
        final File directory = new File(directoryPath);

        // 确保目录存在且是一个目录
        if (directory.exists() && directory.isDirectory()) {
            final File[] files = directory.listFiles();
            if (files != null) {
                for (final File file : files) {
                    // 删除文件
                    if (!file.isDirectory()) {
                        file.delete();
                        log.warn("删除文件：" + file.getAbsolutePath());
                    }
                }
            }
        } else {
            log.error("目录不存在或不是一个目录：" + directoryPath);
        }
    }
}
