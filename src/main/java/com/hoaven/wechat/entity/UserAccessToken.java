package com.hoaven.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/21.
 */
@Getter
@Setter
public class UserAccessToken extends WeChatEntity {
    private String access_token;
    private String expires_in;
    //用于刷新access_token
    private String refresh_token;
    //用户的唯一标识
    private String openid;
    //用户授权的作用域
    private String scope;
}
