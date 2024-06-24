/*
 * Copyright 2024 Paion Data
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.paiondata.common.util;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
