package com.paiondata.common.util;

import com.paiondata.TranslationMojo;
import com.paiondata.common.constant.MessageConstant;
import com.paiondata.common.entity.FileResult;
import com.paiondata.common.exception.DirectoryException;
import com.paiondata.common.exception.FileDeleteException;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class DirectoryCheckUtil {

    // 生成文件哈希的方法
    public static Map<String, String> generateFileHash(List<String> files) throws IOException, NoSuchAlgorithmException {
        Map<String, String> map = new HashMap<>();
        for (String file : files) {
            File toFile = new File(file);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            FileInputStream inputStream = new FileInputStream(toFile);
            byte[] byteArray = new byte[1024];
            int bytesCount;
            while ((bytesCount = inputStream.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            inputStream.close();
            byte[] bytes = digest.digest();
            StringBuilder hashBuilder = new StringBuilder();
            for (byte b : bytes) {
                hashBuilder.append(String.format("%02x", b));
            }
            map.put(file, hashBuilder.toString());
        }

        return map;
    }

    // 获取当前路径md文件列表的方法
    public static List<String> getCurrentFileList(String path) {
        // 实现获取当前输入目录的文件列表的逻辑
        List<String> markdownFiles = new ArrayList<>();
        File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            getAllNonEmptyMarkdownFiles(directory, markdownFiles);
        } else {
            throw new DirectoryException(MessageConstant.DIRECTORY_ERROR + path);
        }
        return markdownFiles;
    }

    private static void getAllNonEmptyMarkdownFiles(File directory, List<String> markdownFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getAllNonEmptyMarkdownFiles(file, markdownFiles);
                } else if (file.isFile() && file.getName().toLowerCase().endsWith(".md") && file.length() > 0) {
                    markdownFiles.add(directory + File.separator +file.getName());
                }
            }
        }
    }

    // 删除文件的方法
    public static void deleteFile(List<String> fileList, String outputPath) {
        for (String file : fileList) {
            String[] split = file.split("/");
            file = outputPath+ File.separator + split[split.length - 1].replace("docs\\", "");
            // 创建 File 对象
            File deleteFile = new File(file);

            // 检查文件是否存在
            if (deleteFile.exists()) {
                // 尝试删除文件
                if (!deleteFile.delete()) {
                    throw new FileDeleteException(MessageConstant.FILE_DELETE_ERROR + file);
                }
            } else {
                throw new FileDeleteException(MessageConstant.FILE_NOT_EXIST + file);
            }
        }
    }

    // 目录检查
    public static FileResult syncFileWithMap(String directory, Map<String, String> map) {
        List<String> addedKeys = new ArrayList<>();
        List<String> updatedKeys = new ArrayList<>();
        List<String> deletedKeys = new ArrayList<>();

        // 如果目录不存在，则创建
        Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                log.info("目录已创建：" + directory);
            } catch (IOException e) {
                log.error("无法创建目录：" + directory);
            }
        }

        File file = new File(directory, "file.txt");

        // 将txt内容存入Map中
        Map<String, String> fileMap = new HashMap<>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(":");
                    fileMap.put(parts[0], parts[1]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // 处理文本内容与实际不符的md文件
        List<String> fileList = getCurrentFileList(directory);
        for (String f : fileList) {
            String[] split = f.split(File.separator);
            String fileName = "docs" + File.separator + split[split.length - 1];
            if (!fileMap.containsKey(fileName)) {
                log.warn("移除文件：" + directory + File.separator + split[split.length - 1]);

                String[] split1 = f.split(File.separator);
                fileName = directory + File.separator + split1[split.length - 1];
                File file2bDelete = new File(fileName);
                // 检查文件是否存在
                if (file2bDelete.exists()) {
                    // 尝试删除文件
                    if (!file2bDelete.delete()) {
                        throw new FileDeleteException(MessageConstant.FILE_DELETE_ERROR + fileName);
                    }
                } else {
                    throw new FileDeleteException(MessageConstant.FILE_NOT_EXIST + fileName);
                }
            }
        }

        for (String key : fileMap.keySet()) {
            String[] split = key.split("/");
            String afterSplit = TranslationMojo.DEFAULT_OUTPUT_PATH + File.separator + split[split.length - 1];
            if (!fileList.contains(afterSplit)) {
                log.info("新增文件：" + key);
                addedKeys.add(key);
            }
        }

        // 将现在目录的map与原文件map做对比
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String oldValue = fileMap.get(key);
            if (oldValue == null) {
                log.info("新增文件：" + key);
                addedKeys.add(key);
            } else if (!oldValue.equals(value)) {
                log.info("更新文件：" + key);
                updatedKeys.add(key);
            }
        }

        // 将现在目录map存入原文件map
        fileMap.putAll(map);

        // 找到删除的K
        for (String key : fileMap.keySet()) {
            if (!map.containsKey(key)) {
                log.warn("移除文件：" + key);
                deletedKeys.add(key);
            }
        }

        // 移除删除的K
        for (String deletedKey : deletedKeys) {
            fileMap.remove(deletedKey);
        }

        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (Map.Entry<String, String> entry : fileMap.entrySet()) {
                writer.write(entry.getKey() + ":" + entry.getValue());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return new FileResult(addedKeys, updatedKeys, deletedKeys);
    }

}
