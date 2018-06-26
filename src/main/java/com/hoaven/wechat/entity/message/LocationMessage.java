package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20
 * 地理位置消息.
 */
@Getter
@Setter
public class LocationMessage extends WeChatMessage implements AcceptableXmlMessage {

    public LocationMessage() {
        this.setMsgType(MsgType.location);
    }

    //消息id，64位整型
    private String msgId;
    //地理位置维度
    private Double locationX;
    //地理位置经度
    private Double locationY;
    //地图缩放大小
    private Integer scale;
    //地理位置信息
    private String label;
}
