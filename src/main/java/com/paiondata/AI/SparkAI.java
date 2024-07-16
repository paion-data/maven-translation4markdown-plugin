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
package com.paiondata.AI;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import lombok.extern.slf4j.Slf4j;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * SparkAI 类提供了生成特定消息回复的功能.
 */
@Slf4j
public class SparkAI extends WebSocketListener {
    // 地址与鉴权信息  https://spark-api.xf-yun.com/v1.1/chat   1.5地址  domain参数为general
    // 地址与鉴权信息  https://spark-api.xf-yun.com/v2.1/chat   2.0地址  domain参数为generalv2
    public static final String hostUrl = "https://spark-api.xf-yun.com/v4.0/chat";

    public static List<RoleContent> historyList=new ArrayList<>(); // 对话历史存储集合

    public static String totalAnswer=""; // 大模型的答案汇总

    // 环境治理的重要性  环保  人口老龄化 我爱我的祖国
    public static  String NewQuestion = "";

    public static final Gson gson = new Gson();

    // 个性化参数
    private String userId;
    private static Boolean wsCloseFlag;

    private static Boolean totalFlag=true; // 控制提示用户是否输入

    /**
     * 构造函数
     * @param userId 用户ID
     * @param wsCloseFlag 关闭状态
     */
    public SparkAI(String userId, Boolean wsCloseFlag) {
        this.userId = userId;
        this.wsCloseFlag = wsCloseFlag;
    }

    /**
     * 获取AI针对特定内容的回答。
     *
     * @param appid      应用ID，用于识别调用者身份。
     * @param apiKey     API密钥，用于接口访问授权。
     * @param apiSecret  API密钥对应的密钥，确保请求的安全性。
     * @param content    用户提出的问题或需要分析的文本内容。
     *
     * @return           AI生成的回答内容。若发生错误，则返回错误信息。
     *
     * @throws Exception 包含可能抛出的各种异常，如网络问题、解析异常等。
     */
    public static String getAnswer(String appid, String apiKey, String apiSecret, String content) {
        try {
            totalAnswer = "";
            // 构建鉴权url
            String authUrl = getAuthUrl(hostUrl, apiKey, apiSecret);
            OkHttpClient client = new OkHttpClient.Builder().build();
            String url = authUrl.toString().replace("http://", "ws://").replace("https://", "wss://");
            Request request = new Request.Builder().url(url).build();
            WebSocket webSocket = client.newWebSocket(request, new SparkAI("", false));

            // 构造发送给 AI 的消息
            JSONObject requestJson = new JSONObject();
            JSONObject header = new JSONObject();
            header.put("app_id", appid);
            header.put("uid", UUID.randomUUID().toString().substring(0, 10));
            JSONObject parameter = new JSONObject();
            JSONObject chat = new JSONObject();
            chat.put("domain", "4.0Ultra");
            chat.put("temperature", 0.5);
            chat.put("max_tokens", 8192);
            parameter.put("chat", chat);
            JSONObject payload = new JSONObject();
            JSONObject message = new JSONObject();
            JSONArray textArray = new JSONArray();
            textArray.add(new JSONObject().fluentPut("role", "user").fluentPut("content", content));
            message.put("text", textArray);
            payload.put("message", message);
            requestJson.put("header", header);
            requestJson.put("parameter", parameter);
            requestJson.put("payload", payload);

            // 发送消息给 AI
            webSocket.send(requestJson.toString());

            // 等待结果返回
            while (!wsCloseFlag) {
                Thread.sleep(200);
            }

            // 返回大模型的答案汇总
            return totalAnswer;
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    /**
     * 判断是否可以向历史记录中添加新的内容。
     * <p>
     * 考虑到历史记录的最大限制约为12,000字符，此方法检查当前历史记录的总长度。
     * 如果超过限制，则从历史记录中移除最早添加的5条记录，并返回false表示无法添加更多内容。
     * 否则，返回true表示可以安全添加新内容。
     *
     * @return 如果可以添加更多历史记录内容则返回true，否则返回false。
     */
    public static boolean canAddHistory(){  // 由于历史记录最大上线1.2W左右，需要判断是能能加入历史
        int history_length=0;
        for(RoleContent temp:historyList){
            history_length=history_length+temp.content.length();
        }
        if(history_length>12000){
            historyList.remove(0);
            historyList.remove(1);
            historyList.remove(2);
            historyList.remove(3);
            historyList.remove(4);
            return false;
        }else{
            return true;
        }
    }

    /**
     * MyThread类继承自Thread，用于通过WebSocket发送AI对话请求并管理历史对话记录。
     *
     * @method run
     *
     * @description 此方法构建一个包含历史对话与新问题的JSON请求体，并通过WebSocket发送给AI服务器。
     *              它会遍历历史记录列表`historyList`，将每一条对话内容添加至请求中，然后加入最新的问题内容。
     *              发送请求后，线程会等待直到收到回复或发生异常。完成后关闭WebSocket连接。
     */
    class MyThread extends Thread {
        private WebSocket webSocket;

        /**
         * 构造器，初始化WebSocket对象。
         *
         * @param webSocket 用于通信的WebSocket实例。
         */
        public MyThread(WebSocket webSocket) {
            this.webSocket = webSocket;
        }

        /**
         * 重写Thread的run方法以执行发送消息至AI服务器的任务。
         *
         * @param appid 应用程序ID。
         */
        public void run(String appid) {
            try {
                JSONObject requestJson=new JSONObject();

                JSONObject header=new JSONObject();  // header参数
                header.put("app_id",appid);
                header.put("uid",UUID.randomUUID().toString().substring(0, 10));

                JSONObject parameter=new JSONObject(); // parameter参数
                JSONObject chat=new JSONObject();
                chat.put("domain","generalv2");
                chat.put("temperature",0.5);
                chat.put("max_tokens",4096);
                parameter.put("chat",chat);

                JSONObject payload=new JSONObject(); // payload参数
                JSONObject message=new JSONObject();
                JSONArray text=new JSONArray();

                // 历史问题获取
                if(historyList.size()>0){
                    for(RoleContent tempRoleContent:historyList){
                        text.add(JSON.toJSON(tempRoleContent));
                    }
                }

                // 最新问题
                RoleContent roleContent=new RoleContent();
                roleContent.role="user";
                roleContent.content=NewQuestion;
                text.add(JSON.toJSON(roleContent));
                historyList.add(roleContent);


                message.put("text",text);
                payload.put("message",message);


                requestJson.put("header",header);
                requestJson.put("parameter",parameter);
                requestJson.put("payload",payload);
                 // System.err.println(requestJson); // 可以打印看每次的传参明细
                webSocket.send(requestJson.toString());
                // 等待服务端返回完毕后关闭
                while (true) {
                    // System.err.println(wsCloseFlag + "---");
                    Thread.sleep(200);
                    if (wsCloseFlag) {
                        break;
                    }
                }
                webSocket.close(1000, "");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 重写WebSocketListener的onOpen方法，当WebSocket连接成功建立时被调用。
     *
     * @param webSocket 当前建立连接的WebSocket实例。
     * @param response  与WebSocket连接建立相关的响应信息。
     *
     * @description 该方法创建一个新的MyThread线程实例，将刚建立连接的WebSocket对象传递给它，
     *              并启动该线程以开始处理发送消息到AI服务器的逻辑。这通常涉及到初始化对话、发送历史消息等内容。
     */
    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        super.onOpen(webSocket, response);
        MyThread myThread = new MyThread(webSocket);
        myThread.start();
    }

    /**
     * 重写WebSocketListener的onMessage方法，处理接收到的WebSocket消息。
     *
     * @param webSocket 当前WebSocket连接实例。
     * @param text      从服务器接收到的消息内容，格式为JSON字符串。
     *
     * @description 该方法首先将接收到的JSON字符串解析为`JsonParse`对象。如果解析出的`header.code`非零，
     *              表示有错误发生，记录错误日志并关闭WebSocket连接。接着，遍历解析得到的回复内容列表，
     *              将所有回复内容累加到`totalAnswer`中。当接收到的消息标示着回复完成（`header.status == 2`），
     *              方法会根据`canAddHistory()`的结果决定是否添加新的对话记录到历史列表中，同时设置标志位准备关闭连接。
     */
    @Override
    public void onMessage(WebSocket webSocket, String text) {
        JsonParse myJsonParse = gson.fromJson(text, JsonParse.class);
        if (myJsonParse.header.code != 0) {
            log.error("发生错误，错误码为：" + myJsonParse.header.code);
            log.error("本次请求的sid为：" + myJsonParse.header.sid);
            webSocket.close(1000, "");
        }
        List<Text> textList = myJsonParse.payload.choices.text;
        for (Text temp : textList) {
            totalAnswer=totalAnswer+temp.content;
        }
        if (myJsonParse.header.status == 2) {
            // 可以关闭连接，释放资源
            if(canAddHistory()){
                RoleContent roleContent=new RoleContent();
                roleContent.setRole("assistant");
                roleContent.setContent(totalAnswer);
                historyList.add(roleContent);
            }else{
                historyList.remove(0);
                RoleContent roleContent=new RoleContent();
                roleContent.setRole("assistant");
                roleContent.setContent(totalAnswer);
                historyList.add(roleContent);
            }
            wsCloseFlag = true;
            totalFlag=true;
        }
    }

    /**
     * 重写WebSocketListener的onFailure方法，处理WebSocket连接失败的情况。
     *
     * @param webSocket 当前WebSocket连接实例。
     * @param t         抛出的异常对象，指示失败的具体原因。
     * @param response  与失败尝试关联的HTTP响应（如果有）。
     *
     * @description 当WebSocket连接尝试失败或者在连接过程中遇到错误时，此方法会被调用。
     *              它记录错误日志，包括HTTP响应状态码和响应体（如果可用）。如果响应码不是预期的WebSocket握手成功码（101），
     *              则记录连接失败的信息，并退出应用程序。注意，此方法还会捕获并打印响应体读取时可能发生的IO异常。
     */
    @Override
    public void onFailure(WebSocket webSocket, Throwable t, Response response) {
        super.onFailure(webSocket, t, response);
        try {
            if (null != response) {
                int code = response.code();
                log.error("onFailure code:" + code);
                log.error("onFailure body:" + response.body().string());
                if (101 != code) {
                    log.error("connection failed");
                    System.exit(0);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * 生成带有鉴权信息的WebSocket服务URL。
     *
     * @param hostUrl   WebSocket服务的基础URL地址。
     * @param apiKey    API密钥，用于身份验证。
     * @param apiSecret API密钥对应的密钥，用于签名计算。
     *
     * @return          经过鉴权处理后的完整WebSocket URL。
     *
     * @throws Exception 如果在处理URL、日期格式化或加密过程中发生异常。
     *
     * @description 该方法首先解析基础URL，然后构建一个包含当前日期的预处理字符串，
     *              使用HMAC-SHA256算法和API密钥对应的密钥对此字符串进行签名。之后，将API密钥、算法信息、
     *              以及签名等组合成Authorization头部信息，并将其以及其他必要参数作为查询参数追加到URL上。
     *              最终返回这个包含全部鉴权信息的WebSocket连接URL。
     */
    public static String getAuthUrl(String hostUrl, String apiKey, String apiSecret) throws Exception {
        URL url = new URL(hostUrl);
        // 时间
        SimpleDateFormat format = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = format.format(new Date());
        // 拼接
        String preStr = "host: " + url.getHost() + "\n" +
                "date: " + date + "\n" +
                "GET " + url.getPath() + " HTTP/1.1";
        // System.err.println(preStr);
        // SHA256加密
        Mac mac = Mac.getInstance("hmacsha256");
        SecretKeySpec spec = new SecretKeySpec(apiSecret.getBytes(StandardCharsets.UTF_8), "hmacsha256");
        mac.init(spec);

        byte[] hexDigits = mac.doFinal(preStr.getBytes(StandardCharsets.UTF_8));
        // Base64加密
        String sha = Base64.getEncoder().encodeToString(hexDigits);
        // System.err.println(sha);
        // 拼接
        String authorization = String.format("api_key=\"%s\", algorithm=\"%s\", headers=\"%s\", signature=\"%s\"", apiKey, "hmac-sha256", "host date request-line", sha);
        // 拼接地址
        HttpUrl httpUrl = Objects.requireNonNull(HttpUrl.parse("https://" + url.getHost() + url.getPath())).newBuilder().//
                addQueryParameter("authorization", Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8))).//
                addQueryParameter("date", date).//
                addQueryParameter("host", url.getHost()).//
                build();

        // System.err.println(httpUrl.toString());
        return httpUrl.toString();
    }

    /**
     * 返回的json结果拆解
     */
    class JsonParse {
        Header header;
        Payload payload;
    }

    /**
     * json解析实体类
     */
    class Header {
        int code;
        int status;
        String sid;
    }

    /**
     * json解析实体类
     */
    class Payload {
        Choices choices;
    }

    /**
     * json解析实体类
     */
    class Choices {
        List<Text> text;
    }

    /**
     * json解析实体类
     */
    class Text {
        String role;
        String content;
    }

    /**
     * 角色实体类
     */
    class RoleContent{
        String role;
        String content;

        /**
         * 获取角色
         * @return 角色
         */
        public String getRole() {
            return role;
        }

        /**
         * 设置角色
         * @param role 角色
         */
        public void setRole(String role) {
            this.role = role;
        }

        /**
         * 获取内容
         * @return 内容
         */
        public String getContent() {
            return content;
        }

        /**
         * 设置内容
         * @param content 内容
         */
        public void setContent(String content) {
            this.content = content;
        }
    }
}
