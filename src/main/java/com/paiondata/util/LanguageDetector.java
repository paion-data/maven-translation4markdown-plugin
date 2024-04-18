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
        if (chineseCount * 10 > nonChineseCount) {
            return "英语";
        } else {
            return "中文";
        }
    }

    public static void main(String[] args) {
        // 测试
        String text1 = "- **DB_USER**：持久性数据库用户名（需要具有读写权限）。\n" +
                "\n" +
                "- **DB_PASSWORD**：持久性数据库用户密码。\n" +
                "\n" +
                "- **DB_URL**：持久性数据库URL，例如 \"jdbc:mysql://localhost/elide?serverTimezone=UTC\"。\n" +
                "\n" +
                "- **DB_DRIVER**：SQL数据库驱动类名，例如 \"com.mysql.jdbc.Driver\"。\n" +
                "\n" +
                "- **DB_DIALECT**：SQL数据库方言名称，例如 \"org.hibernate.dialect.MySQLDialect\"。\n" +
                "\n" +
                "- **HIBERNATE_HBM2DDL_AUTO**：当Web服务启动时如何处理现有的JPA数据库；可以是以下4个值之一：\n" +
                "\n" +
                "    1. _validate_：验证模式是否匹配，不对数据库的模式进行任何更改。_这是**HIBERNATE_HBM2DDL_AUTO**的默认值_\n" +
                "    2. _update_：更新模式以反映正在持久化的实体\n" +
                "    3. _create_：创建您的实体所需的模式，销毁任何先前的数据。\n" +
                "    4. _create-drop_：像上面创建一样创建模式，但在会话结束时删除模式。这在开发或测试中非常有用。\n" +
                "\n" +
                "  :::note\n" +
                "\n" +
                "  此属性与[Hibernate `hibernate.hbm2ddl.auto`属性]完全相同。\n" +
                "\n" +
                "  :::\n" +
                "\n" +
                "[Hibernate `hibernate.hbm2ddl.auto`属性]: https://stackoverflow.com/questions/18077327/hibernate-hbm2ddl-auto-possible-values-and-what-they-do\n" +
                "\n" +
                "[Java系统属性]: https://docs.oracle.com/javase/tutorial/essential/environment/sysprop.html\n" +
                "\n" +
                "[操作系统的环境变量]: https://docs.oracle.com/javase/tutorial/essential/environment/env.html";
        String text2 = "你好，世界！";

        System.out.println(detectLanguage(text1));  // 输出：EN
        System.out.println(detectLanguage(text2));  // 输出：CHS
    }
}
