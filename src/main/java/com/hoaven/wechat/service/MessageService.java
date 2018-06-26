package com.hoaven.wechat.service;

import com.hoaven.wechat.ConstantsPlaceholder;
import com.hoaven.wechat.WeChatException;
import com.hoaven.wechat.WeChatConfig;
import com.hoaven.wechat.entity.WeChatEntity;
import com.hoaven.wechat.entity.message.NewsMessage;
import com.hoaven.wechat.entity.message.TextMessage;
import com.hoaven.wechat.utils.HttpUtils;

/**
 * Created hehuanchun on 2016/10/20.
 */
public class MessageService {
    /**
     * 发送文本消息
     * @param accessToken
     * @param openId
     * @param content
     * @return
     */
    public boolean sendTextMessage(final String accessToken, final String openId, final String content) {
        TextMessage textMessage = new TextMessage();
        textMessage.setOpenId(openId);
        textMessage.setContent(content);
        String requestJson = textMessage.toReturnJSON();

        String apiUrl = WeChatConfig.getUrl("send.message").replace(ConstantsPlaceholder.ACCESS_TOKEN, accessToken);
        WeChatEntity entity = HttpUtils.post(requestJson, apiUrl, WeChatEntity.class);

        if (entity.isSuccess()) {
            return Boolean.TRUE;
        } else {
            throw new WeChatException("send text error " + entity.toErrorString());
        }
    }

    /**
     * 发送图文消息
     * @param accessToken
     * @param openId
     * @param title
     * @param description
     * @param url
     * @param picurl
     * @return
     */
    public boolean sendNewsMessage(final String accessToken,
                                   final String openId,
                                   final String title,
                                   final String description,
                                   final String url,
                                   final String picurl) {
        NewsMessage newsMessage = new NewsMessage();
        newsMessage.setOpenId(openId);
        NewsMessage.News news = new NewsMessage.News();
        news.addArticle(new NewsMessage.News.Article(title, description, url, picurl));
        newsMessage.setNews(news);
        String requestJson = newsMessage.toReturnJSON();

        String apiUrl = WeChatConfig.getUrl("send.message").replace(ConstantsPlaceholder.ACCESS_TOKEN, accessToken);
        WeChatEntity entity = HttpUtils.post(requestJson, apiUrl, WeChatEntity.class);

        if (entity.isSuccess()) {
            return Boolean.TRUE;
        } else {
            throw new WeChatException("send news error " + entity.toErrorString());
        }
    }


}
