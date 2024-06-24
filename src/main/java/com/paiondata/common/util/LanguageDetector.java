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

/**
 * 语言检测器工具类，用于识别给定文本是中文还是英文。
 */
public class LanguageDetector {

    /**
     * 检测输入文本的语言类型。
     * <p>
     * 通过统计文本中中文字符（Unicode范围为4E00至9FFF）的数量与非中文字符数量的比值来判断。
     * 如果中文字符的比例远高于非中文字符，则认为是中文文本；否则，简单判断为英文文本。
     * 注意：这种方法适用于简化的语言识别场景，对于混合语言或非中英文文本可能无法准确判断。
     *
     * @param text 需要检测的语言文本。
     *
     * @return String 识别结果，可能是"中文"或"英语"。
     */
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
        if (chineseCount * 10 > nonChineseCount) {
            return "英语";
        } else {
            return "中文";
        }
    }
}
