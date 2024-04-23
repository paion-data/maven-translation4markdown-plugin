# AI Markdown Maven翻译插件

**View English Version**
- [Click here to view the English version](README.md)

这个工具利用 AI 服务实现了 Markdown 文档的翻译功能。它可以将 Markdown 文件翻译成英文，并将结果保存到新的文件中，支持多种模式。

## 导入Maven插件
**Maven Central:**
```
<plugin>
    <groupId>com.paiondata</groupId>
        <artifactId>ai-translation4markdown-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <executions>
            <execution>
                <phase>compile</phase>
                <goals>
                    <goal>translate</goal>
                </goals>
            </execution>
        </executions>
</plugin>
```

## 注册讯飞星火大模型

1. **准备讯飞服务接口认证信息**
    - https://passport.xfyun.cn/register 在讯飞开放平台注册账号。
    - https://console.xfyun.cn/services/bm35 购买服务并保存 APPID、APISecret、APIKey。

## 配置验证信息

1. **在 Maven 插件的 POM 文件中直接配置（不推荐这种方法）**
```
<plugin>
    <groupId>com.paiondata</groupId>
        <artifactId>ai-translation4markdown-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <configuration>
            <appid>YOUR_APPID</appid>
            <apiSecret>YOUR_API_SECRET</apiSecret>
            <apiKey>YOUR_API_KEY</apiKey>
        </configuration>
        <executions>
            <execution>
                <phase>compile</phase>
                <goals>
                    <goal>translate</goal>
                </goals>
            </execution>
        </executions>
</plugin>
```

2. **在 Maven 的`settings.xml`文件中配置**
    - 可以在 Maven 的 settings.xml 文件中配置参数，这样在执行 Maven 命令时不需要在命令行中指定参数。settings.xml 文件通常位于 ${maven.home}/conf 目录下
```
<settings>
    ...
    <profiles>
        <profile>
            <id>translation-params</id>
            <properties>
                <appid>your_app_id</appid>
                <apiSecret>your_api_secret</apiSecret>
                <apiKey>your_api_key</apiKey>
            </properties>
        </profile>
    </profiles>
    ...
</settings>
```
3. **在 Maven 插件的 POM 文件中添加占位符**
```
<plugin>
    <groupId>com.paiondata</groupId>
        <artifactId>ai-translation4markdown-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <configuration>
            <appid>${appid}</appid>
            <apiSecret>${apiSecret}</apiSecret>
            <apiKey>${apiKey}</apiKey>
        </configuration>
        <executions>
            <execution>
                <phase>compile</phase>
                <goals>
                    <goal>translate</goal>
                </goals>
            </execution>
        </executions>
</plugin>
```

## 运行插件
1. **请在项目的根路径下创建文件夹docs，并将需要翻译的 Markdown 文件放入该文件夹中**
    - 翻译后的文件会存放在`i18n/zh-cn/docusaurus-plugin-content-docs/current`下
2. **在终端中使用`mvn clean install`运行插件**
    - 通过配置文件运行，请使用`mvn clean install -P translation-params`运行插件