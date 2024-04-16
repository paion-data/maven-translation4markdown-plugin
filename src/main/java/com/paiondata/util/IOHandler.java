package com.paiondata.util;

import com.paiondata.entity.MarkdownFileContent;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class IOHandler {

    // 读取文件
    public static MarkdownFileContent readMarkdownFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {
            StringBuilder contentBuilder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                contentBuilder.append(line).append("\n");
            }
            String content = contentBuilder.toString().trim();

            if (content.isEmpty()) {
                throw new IOException("输入文件为空");
            }
            String fileName = Paths.get(filePath).getFileName().toString();
            return new MarkdownFileContent(fileName, content);
        }
    }

    public static void createMarkdownFile(String filePath, String content) throws IOException {
        Path file = Paths.get(filePath);

        // 自动创建缺失的目录结构
        Files.createDirectories(file.getParent());

        try (OutputStreamWriter writer = new OutputStreamWriter(
                Files.newOutputStream(file, StandardOpenOption.CREATE, StandardOpenOption.WRITE),
                StandardCharsets.UTF_8)) {
            writer.write(content);
        }
    }
}
