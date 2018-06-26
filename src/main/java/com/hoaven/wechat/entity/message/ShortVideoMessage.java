package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 */
@Getter
@Setter
public class ShortVideoMessage extends WeChatMessage implements AcceptableXmlMessage {
    public ShortVideoMessage() {
        this.setMsgType(MsgType.shortvideo);
    }

    //消息id，64位整型
    private String msgId;
    //视频消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String mediaId;
    //视频消息缩略图的媒体id，可以调用多媒体文件下载接口拉取数据。
    private String thumbMediaId;
}
