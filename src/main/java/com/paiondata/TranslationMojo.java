package com.paiondata;

import com.paiondata.entity.SparkInfo;
import com.paiondata.util.MarkdownFileReader;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

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

    String appid = "9ef2faca";
    String apiSecret = "YmU3MGYyYTMxODM4ODFlNmEyOWEwNDk1";
    String apiKey = "5a863ea65f88a876c03c4e7f389afa60";

    public void execute() throws MojoExecutionException {
        // 获取当前输入路径文件列表
        List<String> currentFileList = getCurrentFileList();

        // 获取当前输出路径文件列表
        List<String> currentOutputFileList = getCurrentOutputFileList();

        // 比较文件列表
        List<String> newFiles = getNewFiles(currentFileList, currentOutputFileList);

        // 删除输出目录
        deletedFiles(currentFileList, currentOutputFileList);

        // 执行翻译逻辑
        if (!newFiles.isEmpty()) {
            // 执行翻译逻辑
            translateFiles(newFiles);
        }

    }

    // 获取当前输入文件列表的方法
    private List<String> getCurrentFileList() {
        // 实现获取当前输入目录的文件列表的逻辑
        return MarkdownFileReader.getAllMarkdownFiles(DEFAULT_INPUT_PATH);
    }

    // 获取当前输出文件列表的方法
    private List<String> getCurrentOutputFileList() {
        // 实现获取当前输入目录的文件列表的逻辑
        return MarkdownFileReader.getAllMarkdownFiles(DEFAULT_OUTPUT_PATH);
    }

    // 获取新增文件的方法
    private List<String> getNewFiles(List<String> currentFileList, List<String> currentOutputFileList) {
        List<String> newFiles = new ArrayList<>();
        for (String file : currentFileList) {
            String[] split = file.split("/");
            if (!currentOutputFileList.contains(DEFAULT_OUTPUT_PATH2 + "/" + split[split.length - 1].replace(".md", "-output.md"))) {
                newFiles.add(file);
                System.out.println("新增文件: " + file);
            }
        }
        return newFiles;
    }

    // 获取被删除文件的方法
    private void deletedFiles(List<String> currentFileList, List<String> currentOutputFileList) {
        for (String file : currentOutputFileList) {
            String[] split = file.split("/");
            if (!currentFileList.contains(DEFAULT_INPUT_PATH + "/" + split[split.length - 1].replace("-output.md", ".md"))) {
                // 创建 File 对象
                File deleteFile = new File(file);

                System.out.println(deleteFile.getName());

                // 检查文件是否存在
                if (deleteFile.exists()) {
                    // 尝试删除文件
                    if (deleteFile.delete()) {
                        System.out.println("文件删除成功: " + file);
                    } else {
                        System.out.println("无法删除文件: " + file);
                    }
                } else {
                    System.out.println("文件不存在: " + file);
                }
            }
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
