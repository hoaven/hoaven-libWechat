package com.hoaven.wechat.client;

import com.hoaven.wechat.service.ConfigService;
import com.hoaven.wechat.utils.WeChatUtils;

import java.io.IOException;

/**
 * Created by hehuanchun on 2016/11/8 0008.
 */
public class WeChatClient { //测试
    private static String accessToken = "6_V8VWSad2h1radi4e4zFWAqv79jvcMKjYw3ErLHJtAkvnQ6lt_34k1OCVGvb-U1d26wsVsxW0xkzBNZ3kHCjkLYi8BGFHERSfHdRUX2T-_ZTXBfTXqPcu8n7KSC6oCVMkaZp8VcF8aN9DXuwqHDQdABAYUH";

    public static void main(String[] args) throws IOException {
        ConfigService configService = new ConfigService();
        String menuJson = WeChatUtils.readResourceJsonFile("menu-test.json");
        configService.createMenu(menuJson, accessToken);
    }

}

/**
 * 接口客户端调用问题
 * 1.access_token expired hint: [cZcrPA0812vr47!]-->access_token有效期为7200s
 * 2.域名回调授权错误-->修改[网页授权获取用户基本信息] 的域名
 * 3.invalid openid hint-->绝对是appId、secret和accessToken不匹配
 */
