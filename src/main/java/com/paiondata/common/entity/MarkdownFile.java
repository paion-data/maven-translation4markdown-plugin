package com.paiondata.common.entity;

public class MarkdownFile {
    private String fileName;
    private String content;

    public MarkdownFile(String fileName, String content) {
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
