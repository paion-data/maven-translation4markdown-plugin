---
sidebar_position: 4
title: Configuration
---

[//]: # (Copyright 2024 Paion Data)

[//]: # (Licensed under the Apache License, Version 2.0 &#40;the "License"&#41;;)
[//]: # (you may not use this file except in compliance with the License.)
[//]: # (You may obtain a copy of the License at)

[//]: # (    http://www.apache.org/licenses/LICENSE-2.0)

[//]: # (Unless required by applicable law or agreed to in writing, software)
[//]: # (distributed under the License is distributed on an "AS IS" BASIS,)
[//]: # (WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.)
[//]: # (See the License for the specific language governing permissions and)
[//]: # (limitations under the License.)

This section discusses how to configure the plugin in maven.

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

## Configure Verification Information

There are two ways you can provide your configuration information to the plugin, but I only recommend the latter because
the former is not secure.

**Directly configure in the POM file of the Maven plugin (not recommended)**
```
<plugin>
    <groupId>com.paiondata</groupId>
        <artifactId>ai-translation4markdown-plugin</artifactId>
        <version>1.0-SNAPSHOT</version>
        <configuration>
            <appid>YOUR_APPID</appid>
            <apiSecret>YOUR_API_SECRET</apiSecret>
            <apiKey>YOUR_API_KEY</apiKey>
            <engine>ENGINE_NAME</engine>
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
**Configure in Maven's`settings.xml`file**
    - Parameters can be configured in Maven's settings.xml file so that they do not need to be specified on the command
line when executing Maven commands. The settings.xml file is typically located in the ~/.m2/conf directory.
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
                <engine>ENGINE_NAME</engine>
            </properties>
        </profile>
    </profiles>
    ...
</settings>
```

:::tip

The two models differ in the properties of configuration

- **Iflytek Spark**
    - You can configure it according to the preceding instructions
- **Aliyun Qwen**
    - `appid` and `apiSecret` are not required

For `engine`, please select according to the table below:

| engine |     v1     |     v2     |    v3     |       v4       |
|:------:|:----------:|:----------:|:---------:|:--------------:|
| Spark  | Spark Lite | Spark Pro  | Spark Max | Spark4.0 Ultra |
|  Qwen  | qwen-long  | qwen-turbo | qwen-plus |    qwen-max    |

:::

## Run the Plugin
**Run the plugin in the terminal using`mvn clean install`**
    - To run the plugin through the configuration file, please use`mvn clean install -P translation-params`