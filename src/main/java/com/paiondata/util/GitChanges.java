package com.paiondata.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class GitChanges {

    public static void main(String[] args) {
        String directoryPath = "docs"; // 指定目录路径

        List<String> addedFiles = getChangedFiles(directoryPath, "--diff-filter=A");
        List<String> deletedFiles = getChangedFiles(directoryPath, "--diff-filter=D");
        List<String> modifiedFiles = getChangedFiles(directoryPath, "--diff-filter=M");

        System.out.println("Added files:");
        for (String file : addedFiles) {
            System.out.println(file);
        }

        System.out.println("Deleted files:");
        for (String file : deletedFiles) {
            System.out.println(file);
        }

        System.out.println("Modified files:");
        for (String file : modifiedFiles) {
            System.out.println(file);
        }
    }

    public static List<String> getChangedFiles(String directoryPath, String diffFilter) {
        List<String> changedFiles = new ArrayList<>();
        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("git", "diff", "--name-only", diffFilter, "HEAD^", "HEAD", "--", directoryPath);
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                changedFiles.add(line);
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                System.err.println("Error: Git command exited with non-zero status");
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return changedFiles;
    }
}
