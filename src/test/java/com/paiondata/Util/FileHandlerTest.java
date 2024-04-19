package com.paiondata.Util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.paiondata.entity.FileResult;
import com.paiondata.util.FileHandler;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandlerTest {

    // 临时路径
    @TempDir
    Path tempDir;

    // 测试获取当前目录的方法
    @Test
    public void testGetCurrentFileList() {

        List<String> currentFileList = FileHandler.getCurrentFileList();

        // 验证返回的文件列表非空
        assertNotNull(currentFileList);
        assertFalse(currentFileList.isEmpty(), "Expected non-empty file list");

        // 验证文件列表包含预期的文件名（根据实际测试需求添加）
        assertTrue(currentFileList.contains("docs/2.md"), "Expected file 'docs/2.md' missing");
    }

    // 测试 syncFileWithMap 方法-模拟添加键值对
    @Test
    public void testSyncFileWithMap_AddedKeys() throws Exception {
        String directory = tempDir.toString();
        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("key1", "value1");
        inputMap.put("key2", "value2");

        FileResult result = FileHandler.syncFileWithMap(directory, inputMap);

        // 验证返回的 FileResult 不为 null。
        assertNotNull(result);
        // 验证 FileResult 中的 addedKeys 是否包含了 inputMap 中的所有键，即新添加的键都被正确识别
        assertTrue(result.getAddedKeys().containsAll(inputMap.keySet()));
        // 验证 updatedKeys 和 deletedKeys 列表为空，因为在这个测试用例中我们没有模拟已有文件内容的更新或删除情况
        assertTrue(result.getUpdatedKeys().isEmpty());
        assertTrue(result.getDeletedKeys().isEmpty());

        // 检查文件是否已成功创建在指定目录下。
        File file = new File(directory, "file.txt");
        assertTrue(file.exists());
        // 通过 BufferedReader 读取新创建的文件内容，逐行验证其内容与 inputMap 中的数据一致。
        BufferedReader reader = new BufferedReader(new FileReader(file));
        assertEquals("key1:value1", reader.readLine());
        assertEquals("key2:value2", reader.readLine());
        assertNull(reader.readLine());
        reader.close();
    }

    // 测试 syncFileWithMap 方法-模拟更新键值对
    @Test
    public void testSyncFileWithMap_UpdatedKeys() throws Exception {
        String directory = tempDir.toString();
        File file = new File(directory, "file.txt");

        // 初始化文件
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("key1:initialValue1");
        writer.newLine();
        writer.write("key2:initialValue2");
        writer.close();

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("key1", "updatedValue1"); // 更新现有的key
        inputMap.put("key3", "value3"); // 添加一个key

        FileResult result = FileHandler.syncFileWithMap(directory, inputMap);

        assertNotNull(result);
        assertTrue(result.getAddedKeys().contains("key3"));
        assertTrue(result.getUpdatedKeys().contains("key1"));
        assertTrue(result.getDeletedKeys().contains("key2"));
        assertTrue(file.exists());

        BufferedReader reader = new BufferedReader(new FileReader(file));
        assertEquals("key1:updatedValue1", reader.readLine()); // 更新的值
        assertEquals("key3:value3", reader.readLine()); // 新增的值
        assertNull(reader.readLine());
        reader.close();
    }

    // 测试 syncFileWithMap 方法-模拟删除键值对
    @Test
    public void testSyncFileWithMap_DeletedKeys() throws Exception {
        String directory = tempDir.toString();
        File file = new File(directory, "file.txt");

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("key1:value1");
        writer.newLine();
        writer.write("key2:value2");
        writer.close();

        Map<String, String> inputMap = new HashMap<>();
        inputMap.put("key1", "value1"); // 保持key1

        FileResult result = FileHandler.syncFileWithMap(directory, inputMap);

        assertNotNull(result);
        assertTrue(result.getAddedKeys().isEmpty());
        assertTrue(result.getUpdatedKeys().isEmpty());
        assertTrue(result.getDeletedKeys().contains("key2")); // key2应该被删除
        assertTrue(file.exists());

        BufferedReader reader = new BufferedReader(new FileReader(file));
        assertEquals("key1:value1", reader.readLine()); // 保持的的key还在
        assertNull(reader.readLine()); // 没了
        reader.close();
    }

}
