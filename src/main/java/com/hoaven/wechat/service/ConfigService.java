package com.hoaven.wechat.service;

import com.hoaven.wechat.ConstantsPlaceholder;
import com.hoaven.wechat.WeChatException;
import com.hoaven.wechat.WeChatConfig;
import com.hoaven.wechat.entity.AccessToken;
import com.hoaven.wechat.entity.WeChatEntity;
import com.hoaven.wechat.utils.HttpUtils;

/**
 * Created hehuanchun on 2016/10/21.
 */
public class ConfigService {
    /**
     * 获取access_token
     * access_token是公众号的全局唯一接口调用凭据，公众号调用各接口时都需使用access_token,和网页授权认证凭证access_token不是同一个东西。
     * @param appId
     * @param secret
     * @return
     */
    public AccessToken getAccessToken(String appId, String secret) {
        String apiUrl = WeChatConfig.getUrl("get.access_token")
                .replace(ConstantsPlaceholder.APPID, appId)
                .replace(ConstantsPlaceholder.APPSECRET, secret);
        AccessToken accessToken = HttpUtils.get(apiUrl, AccessToken.class);

        if (accessToken.isSuccess()) {
            return accessToken;
        } else {
            throw new WeChatException("get accesstoken error " + accessToken.toErrorString());
        }
    }

    /**
     * 查询自定义菜单（数据在WeChatEntity中的msg json中）
     * @param accessToken
     * @return
     */
    public boolean verifyAccessToken(String accessToken) {
        String apiUrl = WeChatConfig.getUrl("get.menu")
                .replace(ConstantsPlaceholder.ACCESS_TOKEN, accessToken);
        WeChatEntity entity = HttpUtils.get(apiUrl, WeChatEntity.class);
        if (entity.isSuccess() || "46003".equals(entity.getErrcode())) {
            return Boolean.TRUE;
        } else if ("40001".equals(entity.getErrcode())
                || "40014".equals(entity.getErrcode())
                || "42001".equals(entity.getErrcode())) {
            return false;
        } else {
            throw new WeChatException("verify accessToken error " + entity.toErrorString());
        }
    }

    /**
     * 创建自定义菜单
     * @param menuJson
     * @param accessToken
     * @return
     */
    public boolean createMenu(String menuJson, String accessToken) {
        String apiUrl = WeChatConfig.getUrl("create.menu").replace(ConstantsPlaceholder.ACCESS_TOKEN, accessToken);
        WeChatEntity entity = HttpUtils.post(menuJson, apiUrl, WeChatEntity.class);

        if (entity.isSuccess()) {
            return Boolean.TRUE;
        } else {
            throw new WeChatException("create menu error " + entity.toErrorString());
        }
    }

    /**
     * 删除自定义菜单
     * @param accessToken
     * @return
     */
    public boolean deleteAllMenu(String accessToken) {
        String apiUrl = WeChatConfig.getUrl("delete.menu").replace(ConstantsPlaceholder.ACCESS_TOKEN, accessToken);
        WeChatEntity entity = HttpUtils.get(apiUrl, WeChatEntity.class);

        if (entity.isSuccess()) {
            return Boolean.TRUE;
        } else {
            throw new WeChatException("delete menu error " + entity.toErrorString());
        }
    }
}
