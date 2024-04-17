package com.paiondata;

import com.paiondata.entity.SparkInfo;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

@Mojo(name = "translate", defaultPhase = LifecyclePhase.INSTALL)
public class TranslationMojo extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(property = "appid")
    private String appid;
    @Parameter(property = "apiSecret")
    private String apiSecret;
    @Parameter(property = "apiKey")
    private String apiKey;

    public void execute() throws MojoExecutionException {
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
            CreateClient.sparkTranslate(info);

            // 输出结果
//            getLog().info("翻译结果: " + result);
        } catch (Exception e) {
            throw new MojoExecutionException("执行翻译时出现异常", e);
        }
    }
}
