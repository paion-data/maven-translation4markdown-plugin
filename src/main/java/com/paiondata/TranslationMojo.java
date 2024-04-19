package com.paiondata;

import com.paiondata.entity.FileResult;
import com.paiondata.entity.SparkInfo;
import com.paiondata.util.FileHandler;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

@Mojo(name = "translate", defaultPhase = LifecyclePhase.INSTALL)
public class TranslationMojo extends AbstractMojo {

    public static final String DEFAULT_INPUT_PATH = "docs";

    public static final String DEFAULT_OUTPUT_PATH = "i18n/zh-cn/docusaurus-plugin-content-docs/current";

    public static final String DEFAULT_OUTPUT_PATH2 = "i18n\\zh-cn\\docusaurus-plugin-content-docs\\current";

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

//    @Parameter(property = "appid")
//    private String appid;
//    @Parameter(property = "apiSecret")
//    private String apiSecret;
//    @Parameter(property = "apiKey")
//    private String apiKey;



    public void execute() throws MojoExecutionException {
        // 获取当前输入路径文件列表
        List<String> currentFileList = FileHandler.getCurrentFileList();

        // 生成输入路径文件哈希
        Map<String, String> fileHash;
        try {
            fileHash = FileHandler.generateFileHash(currentFileList);
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        // 获取输出路径文件列表
        FileResult fileResult = FileHandler.syncFileWithMap(DEFAULT_OUTPUT_PATH, fileHash);

        // 删除输出目录
        FileHandler.deletedFiles(fileResult.getDeletedKeys());
        FileHandler.deletedFiles(fileResult.getUpdatedKeys());

        // 执行翻译逻辑
        if (!fileResult.getAddedKeys().isEmpty() || !fileResult.getUpdatedKeys().isEmpty()) {
            // 执行翻译逻辑
            translateFiles(fileResult.getAddedKeys());
            translateFiles(fileResult.getUpdatedKeys());
        }
    }

    // 执行翻译逻辑的方法
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
