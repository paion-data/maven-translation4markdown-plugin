package com.paiondata.util;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.AI.AliDashScopeAI;
import com.paiondata.AI.SparkAI;
import com.paiondata.Exception.TranslationException;
import com.paiondata.entity.AliyunInfo;
import com.paiondata.entity.MarkdownFileContent;
import com.paiondata.entity.SparkInfo;
import java.io.IOException;

public class MarkdownHandler {

    public static void translate(String service, String... params) throws TranslationException, NoApiKeyException, InputRequiredException {
        switch (service) {
            case AliyunInfo.service -> {
                String trans;
                MarkdownFileContent fileContent1 = readFile(params[1]);
                String detectLanguage = LanguageDetector.detectLanguage(fileContent1.getContent());
                String content1 = "请帮我将Markdown文档翻译成" + detectLanguage + ", 这个是提供的Markdown文档:\n"
                        + fileContent1.getContent();
                trans = AliDashScopeAI.callWithMessage(params[0], content1, Boolean.parseBoolean(params[3]));
                createOutputFile(params[2], fileContent1, trans);
            }
            case SparkInfo.service -> {
                String trans;
                MarkdownFileContent fileContent2 = readFile(params[3]);
                String detectLanguage = LanguageDetector.detectLanguage(fileContent2.getContent());
                String content2 = "请帮我将Markdown文档翻译成" + detectLanguage + ", 这个是提供的Markdown文档:\n"
                        + fileContent2.getContent();
                trans = SparkAI.getAnswer(params[0], params[1], params[2], content2);
                createOutputFile(params[4], fileContent2, trans);
            }
            default -> throw new TranslationException("Unsupported translation service: " + service);
        }
    }

    private static MarkdownFileContent readFile(String inputPath) throws TranslationException {
        try {
            return IOHandler.readMarkdownFile(inputPath);
        } catch (IOException e) {
            throw new TranslationException("Error reading Markdown file: " + e.getMessage());
        }
    }

    private static String createOutputFile(String outputPath, MarkdownFileContent fileContent, String trans) throws TranslationException {
        if (trans == null) {
            throw new TranslationException("Translation result is null.");
        }

        String outputFile = outputPath + fileContent.getFileName().replace(".md", "-output.md");
        try {
            IOHandler.createMarkdownFile(outputFile, trans);
            System.out.println("Markdown 文件已创建：" + outputFile);
            return outputFile;
        } catch (IOException e) {
            throw new TranslationException("Error creating Markdown file: " + e.getMessage());
        }
    }
}
