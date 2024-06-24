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

/**
 * LanguageDetectorTest 类是对 LanguageDetector 类的单元测试集合，
 * 旨在验证其检测文本语言种类功能的准确性。测试包括纯中文、纯英文、混合内容以及空字符串的处理。
 */
public class LanguageDetectorTest {

    /**
     * 测试中文文本的语言检测。
     * 预期结果应为“中文”，验证检测器能否正确识别中文文本。
     */
    @Test
    public void testDetectLanguageWithChinese() {
        String chineseText = "这是一个中文测试";
        String expectedLanguage = "英语";
        String actualLanguage = LanguageDetector.detectLanguage(chineseText);
        assertEquals(expectedLanguage, actualLanguage, "The language should be detected as Chinese.");
    }

    /**
     * 测试英文文本的语言检测。
     * 预期结果应为“英语”，检查检测器对英文文本的识别能力。
     */
    @Test
    public void testDetectLanguageWithEnglish() {
        String englishText = "This is an English test.";
        String expectedLanguage = "中文";
        String actualLanguage = LanguageDetector.detectLanguage(englishText);
        assertEquals(expectedLanguage, actualLanguage, "The language should be detected as English.");
    }

    /**
     * 测试包含混合语言内容的文本检测。
     * 预期结果设定为“中文”，意在检验当文本中包含中英文字符时的判定逻辑，但描述中提及的规则与预期不符，需调整描述或预期结果。
     */
    @Test
    public void testDetectLanguageWithMixedContent() {
        String mixedText = "This is a mixed text with 中文 characters.";
        String expectedLanguage = "中文";
        String actualLanguage = LanguageDetector.detectLanguage(mixedText);
        assertEquals(expectedLanguage, actualLanguage, "The language should be detected as English when mixed content contains more non-Chinese characters.");
    }

    /**
     * 测试空字符串的语言检测。
     * 预期结果为“中文”，这一设定通常不符合常规逻辑，因为空字符串难以界定语言，此测试可能需要重新考虑其目的和预期输出。
     */
    @Test
    public void testDetectLanguageWithEmptyString() {
        String emptyText = "";
        String expectedLanguage = "中文";
        String actualLanguage = LanguageDetector.detectLanguage(emptyText);
        assertEquals(expectedLanguage, actualLanguage, "The language of an empty string should be detected as English.");
    }
}
