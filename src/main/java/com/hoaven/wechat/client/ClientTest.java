package com.hoaven.wechat.client;

import com.hoaven.wechat.entity.AccessToken;
import com.hoaven.wechat.service.AuthorizeService;
import com.hoaven.wechat.service.ConfigService;
import org.junit.Test;

/**
 * Created by hoaven on 2017/3/25.
 */
public class ClientTest {
    private static ConfigService configService = new ConfigService();
    private AuthorizeService authorizeService = new AuthorizeService();
    private String appId = "wxaa9e65bbf30533a6";

    @Test
    public void getAccessToken() {
        AccessToken accessToken = configService.getAccessToken("wxaa9e65bbf30533a6", "a912747f3db85e3c065914e3fdd6b254");
        System.out.println(accessToken.getAccess_token());
    }

    @Test
    public void getAuthorizeBaseUrl() {
        System.out.println(authorizeService.getAuthorizeBaseUrl(appId, "http://weixin.leave.shuaizhang.top/rest/oauth2/base/allow", "aboutMe"));
    }
}
