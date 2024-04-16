package com.paiondata.util;

import com.alibaba.dashscope.exception.InputRequiredException;
import com.alibaba.dashscope.exception.NoApiKeyException;
import com.paiondata.AI.AliDashScopeAI;
import com.paiondata.AI.SparkAI;
import com.paiondata.entity.AliyunInfo;
import com.paiondata.entity.MarkdownFileContent;

import com.paiondata.Exception.TranslationException;
import com.paiondata.entity.SparkInfo;

import java.io.IOException;

public class MarkdownHandler {

    public static String translate(String service, String... params) throws TranslationException, NoApiKeyException, InputRequiredException {
        String trans;
        switch (service) {
            case AliyunInfo.service -> {
                MarkdownFileContent fileContent1 = readFile(params[1]);
                String content1 = "请帮我将Markdown文档翻译成" + params[3] + ", 这个是提供的Markdown文档:\n"
                        + fileContent1.getContent();
                trans = AliDashScopeAI.callWithMessage(params[0], content1, Boolean.parseBoolean(params[4]));
                return createOutputFile(params[2], fileContent1, trans);
            }
            case SparkInfo.service -> {
                MarkdownFileContent fileContent2 = readFile(params[3]);
                String content2 = "请帮我将Markdown文档翻译成" + params[5] + ", 这个是提供的Markdown文档:\n"
                        + fileContent2.getContent();
                trans = SparkAI.getAnswer(params[0], params[1], params[2], content2);
                return createOutputFile(params[4], fileContent2, trans);
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

    public static void main(String[] args) {
        try {
            String outputPath = translate("Aliyun", "apiKey", "inputPath", "outputPath", "targetLanguage", "mode");
            // or
            // String outputPath = translate("Spark", "appId", "apiKey", "apiSecret", "inputPath", "outputPath", "targetLanguage");
            System.out.println("Output file: " + outputPath);
        } catch (TranslationException | NoApiKeyException | InputRequiredException e) {
            System.err.println("Translation error: " + e.getMessage());
        }
    }

//    // 翻译Markdown文件, 阿里云
//    public static String transAliyun(String apiKey, String inputPath, String outputPath, String targetLanguage, Boolean mode) throws NoApiKeyException, InputRequiredException {
//        MarkdownFileContent fileContent = null;
//        try {
//            fileContent = IOHandler.readMarkdownFile(inputPath);
//        } catch (IOException e) {
//            System.err.println("读取Markdown文件时出错：" + e.getMessage());
//        }
//        String content = "请帮我将Markdown文档翻译成" + targetLanguage + ", 这个是提供的Markdown文档:\n"
//                + fileContent.getContent();
//        String trans = AliDashScopeAI.callWithMessage(apiKey, content, mode);
//        return getString(outputPath, fileContent, trans);
//    }
//
//    // 翻译Markdown文件, 星火
//    public static String transSpark(String appId, String apiKey, String apiSecret, String inputPath, String outputPath, String targetLanguage) {
//        MarkdownFileContent fileContent = null;
//        try {
//            fileContent = IOHandler.readMarkdownFile(inputPath);
//        } catch (IOException e) {
//            System.err.println("读取Markdown文件时出错：" + e.getMessage());
//        }
//        String content = "请帮我将Markdown文档翻译成" + targetLanguage + ", 这个是提供的Markdown文档:\n"
//                + fileContent.getContent();
//        String trans = SparkAI.getAnswer(appId, apiKey, apiSecret, content);
//        return getString(outputPath, fileContent, trans);
//    }
//
//    @NotNull
//    private static String getString(String outputPath, MarkdownFileContent fileContent, String trans) {
//        if(trans == null) {
//            System.err.println("存在错误, 翻译结果为空");
//        }
//        String Path = null;
//        try {
//            String[] split = fileContent.getFileName().split(".md");
//            Path = outputPath + split[0] + "-output.md";
//            IOHandler.createMarkdownFile(Path, trans);
//            System.out.println("Markdown 文件已创建：" + Path);
//        } catch (IOException e) {
//            System.err.println("创建Markdown文件时出错：" + e.getMessage());
//        }
//        return Path;
//    }
}
