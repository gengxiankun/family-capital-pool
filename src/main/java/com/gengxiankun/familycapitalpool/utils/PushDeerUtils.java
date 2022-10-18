package com.gengxiankun.familycapitalpool.utils;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URLEncoder;
import java.util.Objects;

/**
 * Push Deer 信息发送工具
 * @author xiankun.geng
 */
public class PushDeerUtils {

    private static final Logger logger = LoggerFactory.getLogger(PushDeerUtils.class);

    private static final String host = "https://api2.pushdeer.com";

    private static final String pushMsgPath = "/message/push";

    private static final OkHttpClient client;

    private PushDeerUtils() {}

    static {
        client = new OkHttpClient();
    }

    /**
     * 发送文字消息
     */
    public static void sendTextMessage(String key, String text, String des) throws Exception {
        pushMsg(key, text, des,"text");
    }

    /**
     * 发送 MarkDown 消息
     */
    public static void sendMarkDownMessage(String key, String text, String des) throws Exception {
        pushMsg(key, text, des,"markdown");
    }

    /**
     * 发送图片消息
     * @param imageSrc 可以是图片的链接或者是base64,但如果是base64建议使用post进行传输
     */
    public static void sendImageMessage(String key, String imageSrc, String des) throws Exception {
        pushMsg(key, imageSrc, des,"image");
    }

    private static void pushMsg(String key, String text, String des, String type) throws Exception {
        String url = host + pushMsgPath + "?text=" + URLEncoder.encode(text, "utf-8") + "&desp=" +
                URLEncoder.encode(des, "utf-8") + "&type=" + type + "&pushkey=" + key;
        if (!isSendMessageSuccess(url)) {
            logger.error("message send fail");
        }
        logger.debug("message send success!");
    }

    private static boolean isSendMessageSuccess(String url) {
        try {
            Request request = new Request.Builder().url(url).build();
            Response response = client.newCall(request).execute();
            String result = Objects.requireNonNull(response.body()).string();
            if(result.length() == 0){
                return false;
            }
            PushDeerResponse dto = JSONObject.parseObject(result, PushDeerResponse.class);
            if (dto != null) {
                if (dto.getCode() == 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    @Data
    public static class PushDeerResponse {

        /**
         * 响应码 0 代表成功
         */
        private int code;

        /**
         * 错误信息
         */
        private String error;

    }

}
