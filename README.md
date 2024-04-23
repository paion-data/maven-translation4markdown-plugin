# AI Markdown Maven Translate Plugin

**查看中文版本**
- [点击这里查看中文版本](README-CN.md)

This tool uses AI services to implement the translation function of Markdown documents. It can translate Markdown files into English and save the results to new files, supporting various modes.

## Import Maven Plugin
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

## Register iFlytek Starfire Mega Model
1. **Prepare iFlytek service interface authentication information**
    - Visit https://passport.xfyun.cn/register to register an account on the iFlytek open platform.
    - Visit https://console.xfyun.cn/services/bm35 to purchase a service and save APPID, APISecret, APIKey.

## Configure Verification Information
1. **Directly configure in the POM file of the Maven plugin (not recommended)**
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
2. **Configure in Maven's`settings.xml`file**
    - Parameters can be configured in Maven's settings.xml file so that they do not need to be specified on the command line when executing Maven commands. The settings.xml file is typically located in the ${maven.home}/conf directory.
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
3. **Configure in Maven's`settings.xml`file**
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

## Run the Plugin
1. **Please create a folder named docs at the root of the project and put the Markdown files to be translated into this folder**
    - Translated files will be stored in`i18n/zh-cn/docusaurus-plugin-content-docs/current`
2. **Run the plugin in the terminal using`mvn clean install`**
    - To run the plugin through the configuration file, please use`mvn clean install -P translation-params`