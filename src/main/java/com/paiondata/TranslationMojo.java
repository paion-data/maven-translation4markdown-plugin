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
package com.paiondata;

import com.paiondata.common.entity.FileResult;
import com.paiondata.common.entity.SparkInfo;
import com.paiondata.common.util.DirectoryUtil;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * TranslationMojo 是一个Maven插件的目标（Mojo），负责在构建生命周期的INSTALL阶段自动执行Markdown文件的翻译任务。
 * 它基于指定的输入和输出路径管理文件的同步，并调用外部服务对新增或更新的文件进行翻译。
 * <p>
 * 特性包括：
 * - 自动检测并翻译新增或修改的Markdown文件。
 * - 支持配置输入输出路径，默认路径分别为 'docs' 和 'i18n/zh-cn/docusaurus-plugin-content-docs/current'。
 * - 集成了文件哈希生成与比较，以确定文件状态（新增、更新、删除）。
 * - 使用Spark大模型服务进行文件内容翻译。
 */
@Mojo(name = "translate", defaultPhase = LifecyclePhase.INSTALL)
public class TranslationMojo extends AbstractMojo {

    /** 默认输入路径常量 */
    public static final String DEFAULT_INPUT_PATH = "docs" + File.separator + "docs";

    /** 默认输出路径常量 */
    public static final String DEFAULT_OUTPUT_PATH = "docs" + File.separator + "i18n" + File.separator + "zh-cn"
            + File.separator + "docusaurus-plugin-content-docs" + File.separator + "current";

    /** Maven项目对象，自动注入 */
    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    /** Spark服务配置参数 */
    @Parameter(property = "appid")
    private String appid;
    @Parameter(property = "apiSecret")
    private String apiSecret;
    @Parameter(property = "apiKey")
    private String apiKey;

    /**
     * 执行Mojo的主要入口方法。
     *
     * @throws MojoExecutionException 如果在执行过程中遇到错误。
     */
    public void execute() throws MojoExecutionException {
        // 获取当前输入路径文件列表
        List<String> currentFileList = DirectoryUtil.getCurrentFileList(DEFAULT_INPUT_PATH);

        // 生成输入路径文件哈希
        Map<String, String> fileHash;
        try {
            fileHash = DirectoryUtil.generateFileHash(currentFileList);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 获取输出路径文件列表
        FileResult fileResult = DirectoryUtil.syncFileWithMap(DEFAULT_OUTPUT_PATH, fileHash);

        // 删除输出目录
        DirectoryUtil.deleteFile(fileResult.getDeletedKeys(), DEFAULT_OUTPUT_PATH);
        DirectoryUtil.deleteFile(fileResult.getUpdatedKeys(), DEFAULT_OUTPUT_PATH);

        // 执行翻译逻辑
        if (!fileResult.getAddedKeys().isEmpty() || !fileResult.getUpdatedKeys().isEmpty()) {
            // 执行翻译逻辑
            translateFiles(fileResult.getAddedKeys());
            translateFiles(fileResult.getUpdatedKeys());
        }
    }

    /**
     * 私有方法，负责具体翻译逻辑的执行。
     *
     * @param files 需要翻译的文件路径列表。
     *
     * @throws MojoExecutionException 如果缺少必要的API配置参数或翻译过程中发生异常。
     */
    private void translateFiles(List<String> files) throws MojoExecutionException {
        // 读取 api 参数
        if (appid == null || appid.isEmpty()) {
            throw new MojoExecutionException("appid 参数未设置");
        }
        if (apiSecret == null || apiSecret.isEmpty()) {
            throw new MojoExecutionException("apiSecret 参数未设置");
        }
        if (apiKey == null || apiKey.isEmpty()) {
            throw new MojoExecutionException("apiKey 参数未设置");
        }

        // 创建 SparkInfo 对象
        SparkInfo info = SparkInfo.builder()
                .appid(appid)
                .apiSecret(apiSecret)
                .apiKey(apiKey)
                .build();

        try {
            // 调用 CreateClient.sparkTranslate 方法进行翻译
            CreateClient.sparkTranslate(info, files);
        } catch (Exception e) {
            throw new MojoExecutionException("执行翻译时出现异常", e);
        }
    }
}
