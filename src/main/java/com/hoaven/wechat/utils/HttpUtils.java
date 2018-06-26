package com.hoaven.wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hoaven.wechat.entity.WeChatEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.Charset;

/**
 * Created hehuanchun on 2016/10/21.
 */

public class HttpUtils {

    private static CloseableHttpClient httpClient;
    private static PoolingHttpClientConnectionManager cm;

    /**
     * 初始化参数
     */
    public static void init() {
        int maxConn = 5;
        cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(maxConn);
        cm.setDefaultMaxPerRoute(maxConn);
        RequestConfig config = RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD_STRICT).build();
        httpClient = HttpClients.custom().setConnectionManager(cm).setDefaultRequestConfig(config).build();
    }

    /**
     * 向微信服务器发送post请求
     * @param requestJson
     * @param url
     * @return  json
     */
    public static String post(String requestJson, String url) {
        try {
            if (httpClient == null) {
                init();
            }
            CloseableHttpResponse response = null;
            try {
                HttpPost post = new HttpPost(url);
                post.setEntity(new StringEntity(requestJson, ContentType.APPLICATION_JSON));
                response = httpClient.execute(post);
                String responseJson = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                return responseJson;
            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 向微信服务器发送post请求
     * @param requestJson
     * @param url
     * @param responseEntityClassName
     * @param <T>
     * @return  object
     */
    public static <T extends WeChatEntity> T post(String requestJson, String url, Class<T> responseEntityClassName) {
        String responseJson = post(requestJson, url);
        JSONObject jsonObject = JSON.parseObject(responseJson);
        T responseEntity = JSON.toJavaObject(jsonObject, responseEntityClassName);
        return responseEntity;
    }

    public static String get(String url) {
        try {
            if (httpClient == null) {
                init();
            }
            CloseableHttpResponse response = null;
            try {
                HttpGet get = new HttpGet(url);
                response = httpClient.execute(get);
                String responseJson = EntityUtils.toString(response.getEntity(), Charset.forName("UTF-8"));
                return responseJson;

            } finally {
                if (response != null) {
                    response.close();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 向微信服务器发送get请求
     * @param url
     * @param responseEntityClassName
     * @param <T>
     * @return  object
     */
    public static <T extends WeChatEntity> T get(String url, Class<T> responseEntityClassName) {
        String responseJson = get(url);
        JSONObject jsonObject = JSON.parseObject(responseJson);
        T responseEntity = JSON.toJavaObject(jsonObject, responseEntityClassName);
        return responseEntity;

    }


}
