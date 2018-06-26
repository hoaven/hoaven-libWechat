package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 */
@Getter
@Setter
public class WeChatMessage {
    public static final VoidMessage EMPTY_MESSAGE = new VoidMessage();

    public static final class VoidMessage extends WeChatMessage implements ReturnableXmlMessage {
        public String toReturnXML() {
            return "";
        }
    }

    //开发者微信号   发送或接受帐号（一个OpenID）
    private String openId;
    private String weixinId;

    //消息创建时间 （整型）
    private Long createTime;
    //消息类型
    private MsgType msgType;

    public TextMessage asTextMessage() {
        return (TextMessage)this;
    }

    /**
     * 接收普通消息
     * 1 文本消息
     * 2 图片消息
     * 3 语音消息
     * 4 视频消息
     * 5 小视频消息
     * 6 地理位置消息
     * 7 链接消息
     * <p>
     * 被动回复用户消息
     * 1 回复文本消息
     * 2 回复图片消息
     * 3 回复语音消息
     * 4 回复视频消息
     * 5 回复音乐消息
     * 6 回复图文消息
     * <p>
     * 发消息
     * 1 发送文本消息
     * 2 发送语音消息
     * 3 发送视频消息
     * 4 发送音乐消息
     * 5 发送图文消息（自定义内容）
     * 6 发送图文消息（微信后台编辑内容）
     * 7 发送卡券
     */

    public static enum MsgType {
        text,   //文本消息  可接收消息推送，可被动回复消息，可api发送消息
        image,  //图片消息  可接收消息推送，可被动回复消息，可api发送消息
        voice,  //语音消息  可接收消息推送，可被动回复消息，可api发送消息
        video,  //视频消息  可接收消息推送，可被动回复消息，可api发送消息
        music,  //音乐消息  可被动回复消息，可api发送消息
        news,   //图文消息（自定义内容）  可被动回复消息，可api发送消息

        wxcard, //发送卡劵
        mpnews, //图文消息（微信后台编辑内容）

        shortvideo,
        location,
        link,
        event,
        transfer_customer_service;
    }

}
