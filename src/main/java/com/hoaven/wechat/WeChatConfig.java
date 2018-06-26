package com.hoaven.wechat;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * 读取配置信息
 * Created hehuanchun on 2016/10/21.
 */
public class WeChatConfig {
    private static Properties urls = new Properties();

    static {
        load();
    }

    private static void load(){
        InputStream inputStream = null;
        try {
            inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("wechat-api.properties");
            urls.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static String getUrl(String key) {
        if (urls == null) {
            throw new WeChatException("wechat config urls no load");
        }
        String url = (String) urls.get(key);
        if (url == null) {
            load();
            url = (String) urls.get(key);
            if(url == null){
                throw new WeChatException(key + " url not find");
            }
        }
        return url;
    }
}
