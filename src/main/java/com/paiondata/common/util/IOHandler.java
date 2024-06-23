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

public class IOHandler {

    // 读取文件
    public static MarkdownFile readMarkdownFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        if (!Files.exists(path) || !Files.isRegularFile(path)) {
            throw new FileNotFoundException("指定的文件不存在或不是一个常规文件：" + filePath);
        }

        String content = Files.readString(path, StandardCharsets.UTF_8).trim(); // 使用Java 11的Files.readString简化读取

        if (content.isEmpty()) {
            throw new IOException("输入文件内容为空");
        }

        return new MarkdownFile(path.getFileName().toString(), content);
    }

    public static void createMarkdownFile(String filePath, String content) throws IOException {
        Path file = Paths.get(filePath);

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
