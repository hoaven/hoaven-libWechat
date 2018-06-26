package com.hoaven.wechat.service;

import com.hoaven.wechat.entity.AccessToken;
import com.hoaven.wechat.entity.UserAccessToken;
import com.hoaven.wechat.entity.WeChatUserInfo;
import com.hoaven.wechat.utils.WeChatUtils;

import java.io.IOException;

/**
 * Created hehuanchun on 2016/10/22.
 */
public abstract class AbstractWeChatClient {

    protected abstract String getAccessToken();

    protected abstract String getAppId();

    protected abstract String getSecret();

    private AuthorizeService authorizeService= new AuthorizeService();
    private ConfigService configService = new ConfigService();
    private MessageService messageService = new MessageService();


    public boolean sendTextMessage(final String openId, final String content) {
        return messageService.sendTextMessage(getAccessToken(), openId, content);
    }

    public boolean sendNewsMessage(final String openId,
                                   final String title,
                                   final String description,
                                   final String url,
                                   final String picurl) throws IOException {
        return messageService.sendNewsMessage(getAccessToken(), openId, title, description, url, picurl);
    }

    /**
     * 获取assess_token(访问凭证)
     * @return
     */
    public AccessToken getAccessTokenFormWeChat() {
        return configService.getAccessToken(getAppId(), getSecret());
    }

    public boolean verifyAccessToken() {
        return configService.verifyAccessToken(getAccessToken());
    }

    public boolean createMenu(String menuJsonFilePath) throws IOException {
        return configService.createMenu(WeChatUtils.readResourceJsonFile(menuJsonFilePath), getAccessToken());
    }

    public boolean deleteAllMenu() throws IOException {
        return configService.deleteAllMenu(getAccessToken());
    }

    public String getAuthorizeBaseUrl(String redirectUri, String state) {
        return authorizeService.getAuthorizeBaseUrl(getAppId(), redirectUri, state);
    }

    public String getAuthorizeUserInfoUrl(String redirectUri, String state) {
        return authorizeService.getAuthorizeUserInfoUrl(getAppId(), redirectUri, state);
    }

    public UserAccessToken getUserAccessTokenByCode(String code) {
        return authorizeService.getUserAccessTokenByCode(getAppId(), getSecret(), code);
    }

    public WeChatUserInfo getWeChatUnSubscribeUserInfo(String userAccessToken, String openId) {
        return authorizeService.getWeChatUnSubscribeUserInfo(userAccessToken, openId);
    }

    public WeChatUserInfo getWeChatSubscribeUserInfo(String openId) {
        return authorizeService.getWeChatSubscribeUserInfo(getAccessToken(), openId);
    }

    public UserAccessToken checkUserAccessToken(String userAccessToken, String openId) {
        return authorizeService.checkUserAccessToken(userAccessToken, openId);
    }

    public UserAccessToken refreshUserAccessToken(String refreshToken) {
        return authorizeService.refreshUserAccessToken(getAppId(), refreshToken);
    }


}
