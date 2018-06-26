package com.hoaven.wechat.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/22.
 */
@Setter
@Getter
public class WeChatUserInfo extends WeChatEntity {
    //用户特权信息
    private String privilege;

    //如果需要在多公众号、移动应用之间做用户共通，则需前往微信开放平台，将这些公众号和应用绑定到一个开放平台账号下，绑定后，
    //一个用户虽然对多个公众号和应用有多个不同的OpenID，但他对所有这些同一开放平台账号下的公众号和应用，只有一个UnionID
    private String unionid;

    //用户的标识，对当前公众账号唯一
    private String openid;
    //用户昵称
    private String nickname;
    //用户的性别，值为1时是男性，值为2时是女性，值为0时是未知
    private Integer sex;
    //所在省份
    private String province;
    //所在城市
    private String city;
    //所在国家
    private String country;
    //用户头像
    private String headimgurl;

    //用户关注状态:1表示已关注，0表示未关注
    private Boolean subscribe;
    //关注时间
    private Long subscribe_time;
    //用户的语言，简体中文为zh_CN
    private String language;
    private String remark;

    private Integer groupid;
    private Integer[] tagid_list;

}
