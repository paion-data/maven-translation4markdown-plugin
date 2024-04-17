package com.paiondata.util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MarkdownFileReader {

    public static List<String> getAllMarkdownFiles(String directoryPath) {
        List<String> markdownFiles = new ArrayList<>();
        File directory = new File(directoryPath);
        if (directory.exists() && directory.isDirectory()) {
            getAllMarkdownFiles(directory, markdownFiles);
        } else {
            System.out.println("Directory does not exist or is not a directory.");
        }
        return markdownFiles;
    }

    private static void getAllMarkdownFiles(File directory, List<String> markdownFiles) {
        File[] files = directory.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    getAllMarkdownFiles(file, markdownFiles);
                } else if (file.isFile() && file.getName().toLowerCase().endsWith(".md")) {
                    markdownFiles.add(directory + "/" +file.getName());
                }
            }
        }
    }

    public static void main(String[] args) {
        String directoryPath = "docs";
        List<String> markdownFiles = getAllMarkdownFiles(directoryPath);
        System.out.println("Markdown files in directory: " + markdownFiles);
    }
}