package com.hoaven.wechat.entity.message.event;

import com.hoaven.wechat.entity.message.AcceptableXmlMessage;
import com.hoaven.wechat.entity.message.EventMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 */
@Getter
@Setter
public class ScanEventMessage extends EventMessage implements AcceptableXmlMessage {
    public ScanEventMessage() {
        this.setEvent(Event.SCAN);
    }

    //事件KEY值，是一个32位无符号整数，即创建二维码时的二维码scene_id
    private String eventKey;
    //二维码的ticket，可用来换取二维码图片
    private String ticket;
}
