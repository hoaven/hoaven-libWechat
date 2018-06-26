package com.hoaven.wechat.entity.message.event;

import com.hoaven.wechat.entity.message.AcceptableXmlMessage;
import com.hoaven.wechat.entity.message.EventMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * Created hehuanchun on 2016/10/20.
 * 扫描带参数二维码事件
 * 用户未关注时，进行关注后的事件推送
 */
@Getter
@Setter
public class SubscribeEventMessage extends EventMessage implements AcceptableXmlMessage {
    public SubscribeEventMessage() {
        this.setEvent(Event.subscribe);
    }

    //事件KEY值，qrscene_为前缀，后面为二维码的参数值
    private String eventKey;
    //二维码的ticket，可用来换取二维码图片
    private String ticket;
}
