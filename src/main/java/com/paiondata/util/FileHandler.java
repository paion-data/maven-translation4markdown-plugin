package com.paiondata.util;

import com.paiondata.entity.FileResult;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileHandler {

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

    // 获取当前输入路径md文件列表的方法
    public static List<String> getCurrentFileList(String inputPath) {
        // 实现获取当前输入目录的文件列表的逻辑
        List<String> markdownFiles = new ArrayList<>();
        File directory = new File(inputPath);
        if (directory.exists() && directory.isDirectory()) {
            getAllNonEmptyMarkdownFiles(directory, markdownFiles);
        } else {
            System.out.println("Directory does not exist or is not a directory.");
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
                    markdownFiles.add(directory + "/" +file.getName());
                }
            }
        }
    }

    // 删除文件的方法
    public static void deletedFiles(List<String> fileList, String outputPath) {
        for (String file : fileList) {
            String[] split = file.split("/");
            file = outputPath+ "/" + split[split.length - 1].replace(".md", "-output.md");
            // 创建 File 对象
            File deleteFile = new File(file);

            System.out.println(deleteFile.getName());

            // 检查文件是否存在
            if (deleteFile.exists()) {
                // 尝试删除文件
                if (!deleteFile.delete()) {
                    System.out.println("无法删除文件: " + file);
                }
            } else {
                System.out.println("文件不存在: " + file);
            }
        }
    }

    // 文件目录操作
    public static FileResult syncFileWithMap(String directory, Map<String, String> map) {
        List<String> addedKeys = new ArrayList<>();
        List<String> updatedKeys = new ArrayList<>();
        List<String> deletedKeys = new ArrayList<>();

        File file = new File(directory, "file.txt");

        // 将现有文件内容存入Map中
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

        // 将现在目录的map与原文件map做对比
        for (Map.Entry<String, String> entry : map.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String oldValue = fileMap.get(key);
            if (oldValue == null) {
                System.out.println("新增文件: " + key);
                addedKeys.add(key);
            } else if (!oldValue.equals(value)) {
                System.out.println("更新文件: " + key);
                updatedKeys.add(key);
            }
        }

        // 将现在目录map存入原文件map
        fileMap.putAll(map);

        // 找到删除的K
        for (String key : fileMap.keySet()) {
            if (!map.containsKey(key)) {
                System.out.println("移除文件: " + key);
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