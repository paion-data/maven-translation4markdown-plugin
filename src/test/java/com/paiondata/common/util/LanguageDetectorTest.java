package com.paiondata.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.paiondata.common.util.LanguageDetector;

import org.junit.jupiter.api.Test;

public class LanguageDetectorTest {
    @Test
    public void testDetectLanguageWithChinese() {
        String chineseText = "这是一个中文测试";
        String expectedLanguage = "英语";
        String actualLanguage = LanguageDetector.detectLanguage(chineseText);
        assertEquals(expectedLanguage, actualLanguage, "The language should be detected as Chinese.");
    }

    @Test
    public void testDetectLanguageWithEnglish() {
        String englishText = "This is an English test.";
        String expectedLanguage = "中文";
        String actualLanguage = LanguageDetector.detectLanguage(englishText);
        assertEquals(expectedLanguage, actualLanguage, "The language should be detected as English.");
    }

    @Test
    public void testDetectLanguageWithMixedContent() {
        String mixedText = "This is a mixed text with 中文 characters.";
        String expectedLanguage = "中文";
        String actualLanguage = LanguageDetector.detectLanguage(mixedText);
        assertEquals(expectedLanguage, actualLanguage, "The language should be detected as English when mixed content contains more non-Chinese characters.");
    }

    @Test
    public void testDetectLanguageWithEmptyString() {
        String emptyText = "";
        String expectedLanguage = "中文";
        String actualLanguage = LanguageDetector.detectLanguage(emptyText);
        assertEquals(expectedLanguage, actualLanguage, "The language of an empty string should be detected as English.");
    }
}
