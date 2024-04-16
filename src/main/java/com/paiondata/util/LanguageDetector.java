package com.paiondata.util;

public class LanguageDetector {

    public static String detectLanguage(String text) {
        // 定义中文字符的 Unicode 范围
        int chineseMin = 0x4E00;
        int chineseMax = 0x9FFF;

        int chineseCount = 0;
        int nonChineseCount = 0;

        // 统计文本中的中文字符数量和非中文字符数量
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c >= chineseMin && c <= chineseMax) {
                chineseCount++;
            } else {
                nonChineseCount++;
            }
        }

        // 如果中文字符数量大于非中文字符数量，则判断为中文；反之则判断为英文
        if (chineseCount > nonChineseCount) {
            return "中文";
        } else {
            return "英语";
        }
    }

    public static void main(String[] args) {
        // 测试
        String text1 = "Hello, world!";
        String text2 = "你好，世界！";

        System.out.println(detectLanguage(text1));  // 输出：EN
        System.out.println(detectLanguage(text2));  // 输出：CHS
    }
}
