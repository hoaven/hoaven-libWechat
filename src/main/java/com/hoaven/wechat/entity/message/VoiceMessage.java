package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 * 语音消息
 */
@Getter
@Setter
public class VoiceMessage extends WeChatMessage implements ReturnableXmlMessage, SendableApiMessage, AcceptableXmlMessage {
    public VoiceMessage() {
        this.setMsgType(MsgType.voice);
    }

    //消息id，64位整型
    private String msgId;
    //语音消息媒体id，可以调用多媒体文件下载接口拉取数据。
    private String mediaId;
    //语音格式，如amr，speex等
    private String format;

    public String toReturnXML() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String toReturnJSON() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
