package com.paiondata.entity;

public class MarkdownFileContent {
    private String fileName;
    private String content;

    public MarkdownFileContent(String fileName, String content) {
        this.fileName = fileName;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public String getContent() {
        return content;
    }
}
