package com.hoaven.wechat.service;

import com.hoaven.wechat.ConstantsPlaceholder;
import com.hoaven.wechat.WeChatConfig;
import com.hoaven.wechat.WeChatException;
import com.hoaven.wechat.entity.UserAccessToken;
import com.hoaven.wechat.entity.WeChatUserInfo;
import com.hoaven.wechat.utils.HttpUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created hehuanchun on 2016/10/21.
 */
public class AuthorizeService {

    /**
     * 获取code(不弹出授权页面)的url
     * @param appId
     * @param redirectUri
     * @param state
     * @return
     */
    public String getAuthorizeBaseUrl(String appId, String redirectUri, String state) {
        try {
            String apiUrl = WeChatConfig.getUrl("authorize.get.code")
                    .replace(ConstantsPlaceholder.APPID, appId)
                    .replace(ConstantsPlaceholder.REDIRECT_URI, URLEncoder.encode(redirectUri, "UTF-8"))
                    .replace(ConstantsPlaceholder.SCOPE, "snsapi_base")
                    .replace(ConstantsPlaceholder.STATE, state);

            return apiUrl;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取code(弹出授权页面)的url
     * @param appId
     * @param redirectUri
     * @param state
     * @return
     */
    public String getAuthorizeUserInfoUrl(String appId, String redirectUri, String state) {
        try {
            String apiUrl = WeChatConfig.getUrl("authorize.get.code")
                    .replace(ConstantsPlaceholder.APPID, appId)
                    .replace(ConstantsPlaceholder.REDIRECT_URI, URLEncoder.encode(redirectUri, "UTF-8"))
                    .replace(ConstantsPlaceholder.SCOPE, "snsapi_userinfo")
                    .replace(ConstantsPlaceholder.STATE, state);
            return apiUrl;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据code获取access_token等信息
     * @param appId
     * @param secret
     * @param code
     * @return
     */
    public UserAccessToken getUserAccessTokenByCode(String appId, String secret, String code) {
        String apiUrl = WeChatConfig.getUrl("useraccesstoken.get")
                .replace(ConstantsPlaceholder.APPID, appId)
                .replace(ConstantsPlaceholder.SECRET, secret)
                .replace(ConstantsPlaceholder.CODE, code);
        UserAccessToken userAccessToken = HttpUtils.get(apiUrl, UserAccessToken.class);

        if (userAccessToken.isSuccess()) {
            return userAccessToken;
        } else {
            throw new WeChatException("get useraccesstoken error " + userAccessToken.toErrorString());
        }
    }

    /**
     * 根据user_access_token获取微信用户信息(outh2弹出框授权后)
     * @param userAccessToken
     * @param openId
     * @return
     */
    public WeChatUserInfo getWeChatUnSubscribeUserInfo(String userAccessToken, String openId) {
        String apiUrl = WeChatConfig.getUrl("useraccesstoken.get.userinfo")
                .replace(ConstantsPlaceholder.ACCESS_TOKEN, userAccessToken)
                .replace(ConstantsPlaceholder.OPENID, openId);
        WeChatUserInfo weChatUserInfo = HttpUtils.get(apiUrl, WeChatUserInfo.class);

        if (weChatUserInfo.isSuccess()) {
            return weChatUserInfo;
        } else {
            throw new WeChatException("get weChat unSubscribe userInfo error " + weChatUserInfo.toErrorString());
        }
    }

    /**
     * 获取已关注用户的基本信息(如：关注时间、用户的语言等,不弹出授权页面)
     * @param accessToken
     * @param openId
     * @return
     */
    public WeChatUserInfo getWeChatSubscribeUserInfo(String accessToken,String openId){
        String apiUrl = WeChatConfig.getUrl("get.user.info")
                .replace(ConstantsPlaceholder.ACCESS_TOKEN, accessToken)
                .replace(ConstantsPlaceholder.OPENID, openId);
        WeChatUserInfo weChatUserInfo = HttpUtils.get(apiUrl, WeChatUserInfo.class);

        if (weChatUserInfo.isSuccess()) {
            return weChatUserInfo;
        } else {
            throw new WeChatException("get weChat subscribe userInfo error" + weChatUserInfo.toErrorString());
        }
    }

    /**
     * 检查网页授权凭证是否有效
     * 正确返回：{ "errcode":0,"errmsg":"ok"}
     * 错误返回：{ "errcode":40003,"errmsg":"invalid openid"}
     * @param userAccessToken
     * @param openId
     * @return
     */
    public UserAccessToken checkUserAccessToken(String userAccessToken, String openId) {
        String apiUrl = WeChatConfig.getUrl("useraccesstoken.check")
                .replace(ConstantsPlaceholder.ACCESS_TOKEN, userAccessToken)
                .replace(ConstantsPlaceholder.OPENID, openId);
        UserAccessToken accessToken = HttpUtils.get(apiUrl, UserAccessToken.class);

        if (accessToken.isSuccess()) {
            return accessToken;
        } else {
            throw new WeChatException("check useraccesstoken error " + accessToken.toErrorString());
        }
    }

    /**
     * 刷新用户授权凭证
     * @param appId
     * @param refreshToken
     * @return
     */
    public UserAccessToken refreshUserAccessToken(String appId, String refreshToken) {
        String apiUrl = WeChatConfig.getUrl("useraccesstoken.refresh")
                .replace(ConstantsPlaceholder.APPID, appId)
                .replace(ConstantsPlaceholder.REFRESH_TOKEN, refreshToken);
        UserAccessToken userAccessToken = HttpUtils.get(apiUrl, UserAccessToken.class);

        if (userAccessToken.isSuccess()) {
            return userAccessToken;
        } else {
            throw new WeChatException("get refresh useraccesstoken error " + userAccessToken.toErrorString());
        }
    }


}
