package com.hoaven.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/21.
 */
@Getter
@Setter
public class AccessToken extends WeChatEntity {
    //网页授权接口调用凭证
    private String access_token;
    //凭证的有效时长，单位是秒
    private Long expires_in;
}
