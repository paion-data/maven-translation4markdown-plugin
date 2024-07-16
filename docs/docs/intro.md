---
sidebar_position: 1
title: Introduction
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

This is a plugin specifically designed for Maven projects, which utilizes advanced AI large model technology to
automatically identify and translate Markdown documents in the project. This plugin is especially suitable for projects
with a large number of documents, such as open source software, internal enterprise knowledge bases, or technical
documentation websites, and it can significantly improve the efficiency of document internationalization efforts.

### Features
- **Automatic identification and translation of Markdown documents**: The plugin can automatically identify and 
translate Markdown documents in the project, and the translated documents will be stored in the
`i18n/zh-cn/docusaurus-plugin-content-docs/current` folder.
- **Support for multiple AI models**: The plugin supports multiple AI models, including Xunfei Spark, Aliyun Qwen, to
understand and generate high-quality multilingual text.
- **Integration with Docusaurus**: The plugin is designed to work seamlessly with Docusaurus, a popular open-source 
static site generator for documentation websites. Ensure that the translated document is compiled and presented 
correctly.

### Usage scenarios
- **Multilingual document support**: For projects that want to quickly scale to the global market, this plugin can 
accelerate the production of multilingual versions of documents.
- **Continuous integration/Continuous Deployment (CI/CD)**: Integration in GitHub Actions or other CI/CD platforms for
- automatic translation and documentation updates after every code commit.
## Getting AI models

### You need to prepare the AI model in advance.

The plugin supports the following AI models:

- [Xunfei Spark](https://xinghuo.xfyun.cn/spark):
  A large language model by iFLYTEK, capable of understanding and generating human-like text. **Billing is on an annual purchase basis.**
    - [Configuration in Spark](Spark.md)

- [Aliyun Qwen](https://bailian.console.aliyun.com/#/home):
  A multi-modal pre-trained model series by Alibaba Cloud, including a powerful language model similar to ChatGPT. **Charges are based on the number of tokens used.**
    - [Configuration in Qwen](Qwen.md)
