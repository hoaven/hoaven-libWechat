package com.hoaven.wechat.utils;


import org.apache.commons.io.IOUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * Created hehuanchun on 2016/10/20.
 */
public class WeChatUtils {
    private WeChatUtils() {
    }

    /**
     * 请求校验，以确保请求来自微信服务器
     *
     * @param token
     * @param timestamp
     * @param nonce
     * @param signature
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static boolean checkSignature(String token, String timestamp, String nonce, String signature) throws NoSuchAlgorithmException {
        String[] arr = new String[]{token, timestamp, nonce};
        StringBuffer sb = new StringBuffer();
        Arrays.sort(arr);
        for (int i = 0; i < arr.length; ++i) {
            sb.append(arr[i]);
        }
        MessageDigest md = MessageDigest.getInstance("SHA-1");
        md.update(sb.toString().getBytes());

        //对拼接后的字符串进行sha1加密
        byte[] digest = md.digest();

        StringBuffer hexstr = new StringBuffer();
        String shaHex = "";
        for (int i = 0; i < digest.length; ++i) {
            shaHex = Integer.toHexString(digest[i] & 255);
            if (shaHex.length() < 2) {
                hexstr.append(0);
            }
            hexstr.append(shaHex);
        }
        return hexstr != null ? hexstr.toString().equals(signature) : false;
    }

    /**
     * 读取json配置资源
     * @param path
     * @return
     * @throws IOException
     */
    public static String readResourceJsonFile(String path) throws IOException {
        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(path);
        return IOUtils.toString(inputStream);
    }

}
