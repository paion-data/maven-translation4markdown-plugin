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
import java.util.Locale;

/**
 * DirectoryUtil 类提供了一系列与文件目录操作相关的实用方法，包括文件哈希生成、目录下Markdown文件的检索、
 * 文件删除、以及目录同步检查等功能，增强了文件管理的便利性和安全性.
 * <p>
 * 主要方法包括：
 * - `generateFileHash`: 生成指定文件列表的SHA-256哈希值映射.
 * - `getCurrentFileList`: 获取指定路径下的所有非空Markdown文件列表.
 * - `getAllNonEmptyMarkdownFiles`: 辅助方法，递归获取目录下的Markdown文件.
 * - `deleteFile`: 根据文件列表删除指定路径下的文件.
 * - `syncFileWithMap`: 检查并同步目录中的文件，记录添加、更新、删除的文件信息，并更新索引文件.
 * <p>
 * 异常处理:
 * - 使用自定义异常`DirectoryException`和`FileDeleteException`来报告目录访问或文件删除过程中的错误.
 * <p>
 * 注意事项:
 * - 依赖于日志框架Slf4j进行日志记录.
 * - 使用了`MessageConstant`类来标准化错误消息.
 * - 在处理文件和目录时，确保了资源的正确关闭以避免潜在的资源泄露.
 */
@Slf4j
public class DirectoryUtil {

    private static final String SEPARATOR = "/";

    private static final String COLON = ":";

    /**
     * 生成文件哈希映射的静态方法.
     * <p>
     * 此方法遍历给定的文件路径列表，为每个文件计算SHA-256哈希值，并将文件路径与其对应的哈希值
     * 以键值对的形式存入一个Map中返回。适用于文件完整性校验、版本控制等场景.
     *
     * @param files 一个包含文件路径的列表，文件路径应为本地系统上的有效路径.
     *
     * @return Map&lt;String, String&gt; 映射了文件路径到其SHA-256哈希值的Map.
     *
     * @throws IOException 如果读取文件时发生I/O错误.
     * @throws NoSuchAlgorithmException 如果请求的摘要算法（SHA-256）在环境中不可用.
     */
    public static Map<String, String> generateFileHash(final List<String> files)
            throws IOException, NoSuchAlgorithmException {
        final Map<String, String> map = new HashMap<>();
        for (final String file : files) {
            final File toFile = new File(file);
            final MessageDigest digest = MessageDigest.getInstance("SHA-256");
            final FileInputStream inputStream = new FileInputStream(toFile);
            final byte[] byteArray = new byte[1024];
            int bytesCount;
            while ((bytesCount = inputStream.read(byteArray)) != -1) {
                digest.update(byteArray, 0, bytesCount);
            }
            inputStream.close();
            final byte[] bytes = digest.digest();
            final StringBuilder hashBuilder = new StringBuilder();
            for (final byte b : bytes) {
                hashBuilder.append(String.format("%02x", b));
            }
            map.put(file, hashBuilder.toString());
        }

        return map;
    }

    /**
     * 获取指定目录下的所有非空Markdown文件路径列表的方法.
     * <p>
     * 此方法检查给定路径是否为存在的目录，如果是，则递归搜索该目录及其子目录下所有的非空Markdown文件（.md扩展名）.
     * 并将这些文件的绝对路径添加到一个列表中返回。如果路径不存在或不是目录，将抛出异常.
     *
     * @param path 字符串类型的目录路径.
     *
     * @return List&lt;String&gt; 包含找到的所有非空Markdown文件路径的列表.
     *
     * @throws DirectoryException 如果提供的路径不存在或不是一个目录.
     */
    public static List<String> getCurrentFileList(final String path) {
        // 实现获取当前输入目录的文件列表的逻辑
        final List<String> markdownFiles = new ArrayList<>();
        final File directory = new File(path);
        if (directory.exists() && directory.isDirectory()) {
            getAllNonEmptyMarkdownFiles(directory, markdownFiles);
        } else {
            throw new DirectoryException(MessageConstant.DIRECTORY_ERROR + path);
        }
        return markdownFiles;
    }

    /**
     * 私有辅助方法，递归获取指定目录及其子目录下所有非空Markdown文件的路径.
     * <p>
     * 此方法遍历指定的目录，对于每个子项，如果它是目录，则递归调用自身；如果它是以.md结尾的文件且非空，
     * 则将其完整路径添加到传入的列表中。该方法帮助构建一个包含项目内所有Markdown文件路径的列表。
     *
     * @param directory 当前正在处理的目录对象。
     * @param markdownFiles 用于收集Markdown文件路径的列表，方法会将找到的文件路径追加到此列表中。
     */
    private static void getAllNonEmptyMarkdownFiles(final File directory, final List<String> markdownFiles) {
        final File[] files = directory.listFiles();
        if (files != null) {
            for (final File file : files) {
                if (file.isDirectory()) {
                    getAllNonEmptyMarkdownFiles(file, markdownFiles);
                } else if (file.isFile() && file.getName().toLowerCase(Locale.ENGLISH).endsWith(".md")
                        && file.length() > 0) {
                    markdownFiles.add(directory + File.separator + file.getName());
                }
            }
        }
    }

    /**
     * 批量删除指定文件的方法.
     * <p>
     * 根据提供的文件路径列表和输出路径，此方法遍历文件列表，构造每个文件在输出路径下的对应路径，
     * 然后尝试删除这些文件。如果文件存在但删除失败，或者文件不存在，都将抛出异常.
     *
     * @param fileList 包含待删除文件路径的列表，路径应为相对于某个基础目录的相对路径。
     * @param outputPath 输出目录的路径，用于构造完整的文件删除路径。
     *
     * @throws FileDeleteException 如果文件删除操作失败，或指定的文件不存在。
     */
    public static void deleteFile(final List<String> fileList, final String outputPath) {
        for (String file : fileList) {
            final String[] split = file.split(SEPARATOR);
            file = outputPath + File.separator + split[split.length - 1].replace("docs\\", "");
            // 创建 File 对象
            final File deleteFile = new File(file);

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

    /**
     * 同步文件目录与Map数据的方法.
     * <p>
     * 此方法同步指定目录下的文件状态与一个Map表示的文件记录。它会检查目录是否存在并创建（如不存在），
     * 然后比较目录内实际文件与Map中记录的差异，执行添加、更新或删除操作以保持两者一致，并记录每种操作的文件列表。
     * 最后，更新或创建一个总结这些记录的txt文件。此方法广泛应用于文件系统的维护和同步场景。
     *
     * @param directory 需要同步的本地目录路径。
     * @param map 一个映射文件名到其状态（或其他元数据）的Map，用于同步操作。
     *
     * @return FileResult 包含同步操作结果的对象，包括新增、更新和删除的文件列表。
     *
     * @throws FileDeleteException 如果在尝试删除文件时遇到错误。
     */
    public static FileResult syncFileWithMap(final String directory, final Map<String, String> map) {
        final List<String> addedKeys = new ArrayList<>();
        final List<String> updatedKeys = new ArrayList<>();
        final List<String> deletedKeys = new ArrayList<>();

        // 如果目录不存在，则创建
        final Path path = Paths.get(directory);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
                log.info("目录已创建：" + directory);
            } catch (final IOException e) {
                log.error("无法创建目录：" + directory);
            }
        }

        final File file = new File(directory, "file.txt");

        // 将txt内容存入Map中
        final Map<String, String> fileMap = new HashMap<>();
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    final String[] parts = line.split(COLON);
                    fileMap.put(parts[0], parts[1]);
                }
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }

        // 处理文本内容与实际不符的md文件
        final List<String> fileList = getCurrentFileList(directory);
        for (final String f : fileList) {
            final String[] split = f.split(File.separator);
            String fileName = "docs" + File.separator + split[split.length - 1];
            if (!fileMap.containsKey(fileName)) {
                log.warn(MessageConstant.REMOVE_FILE + directory + File.separator + split[split.length - 1]);

                final String[] split1 = f.split(File.separator);
                fileName = directory + File.separator + split1[split.length - 1];
                final File file2bDelete = new File(fileName);
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

        for (final String key : fileMap.keySet()) {
            final String[] split = key.split(SEPARATOR);
            final String afterSplit = TranslationMojo.DEFAULT_OUTPUT_PATH + File.separator + split[split.length - 1];
            if (!fileList.contains(afterSplit)) {
                log.info(MessageConstant.ADD_FILE + key);
                addedKeys.add(key);
            }
        }

        // 将现在目录的map与原文件map做对比
        for (final Map.Entry<String, String> entry : map.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            final String oldValue = fileMap.get(key);
            if (oldValue == null) {
                log.info(MessageConstant.ADD_FILE + key);
                addedKeys.add(key);
            } else if (!oldValue.equals(value)) {
                log.info(MessageConstant.UPDATE_FILE + key);
                updatedKeys.add(key);
            }
        }

        // 将现在目录map存入原文件map
        fileMap.putAll(map);

        // 找到删除的K
        for (final String key : fileMap.keySet()) {
            if (!map.containsKey(key)) {
                log.warn(MessageConstant.REMOVE_FILE + key);
                deletedKeys.add(key);
            }
        }

        // 移除删除的K
        for (final String deletedKey : deletedKeys) {
            fileMap.remove(deletedKey);
        }

        // 写入文件
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (final Map.Entry<String, String> entry : fileMap.entrySet()) {
                writer.write(entry.getKey() + COLON + entry.getValue());
                writer.newLine();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        }

        return new FileResult(addedKeys, updatedKeys, deletedKeys);
    }
}
