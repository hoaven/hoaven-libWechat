package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 * 图片消息
 */
@Getter
@Setter
public class ImageMessage extends WeChatMessage implements ReturnableXmlMessage, SendableApiMessage, AcceptableXmlMessage {
    public ImageMessage() {
        this.setMsgType(MsgType.image);
    }

    //消息id，64位整型
    private String msgId;
    //图片链接
    private String picUrl;
    //图片消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String mediaId;

    public String toReturnXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String toReturnJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
