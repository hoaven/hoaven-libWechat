package com.hoaven.wechat.entity.message;

import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;

/**
 * Created hehuanchun on 2016/10/20.
 */
@Getter
@Setter
public class CustomerTransferMessage extends WeChatMessage implements ReturnableXmlMessage {
    public CustomerTransferMessage() {
        this.setMsgType(MsgType.transfer_customer_service);
    }

    String kfAccount = null;

    public CustomerTransferMessage(final WeChatMessage weChatMessage) {
        this.setOpenId(weChatMessage.getOpenId());
        this.setWeixinId(weChatMessage.getWeixinId());
        this.setCreateTime(weChatMessage.getCreateTime());
    }

    public CustomerTransferMessage(final WeChatMessage weChatMessage, final String kfAccount) {
        this.setOpenId(weChatMessage.getOpenId());
        this.setWeixinId(weChatMessage.getWeixinId());
        this.setCreateTime(weChatMessage.getCreateTime());
        this.kfAccount = kfAccount;
    }

    public String toReturnXML() {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        sb.append("<ToUserName><![CDATA[" + this.getOpenId() + "]]></ToUserName>");
        sb.append("<FromUserName><![CDATA[" + this.getWeixinId() + "]]></FromUserName>");
        sb.append("<CreateTime>" + this.getCreateTime() + "</CreateTime>");
        sb.append("<MsgType><![CDATA[transfer_customer_service]]></MsgType>");
        if (StringUtils.isNotEmpty(kfAccount)) {
            sb.append("<TransInfo><KfAccount><![CDATA[" + kfAccount + "]]></KfAccount></TransInfo>");
        }
        sb.append("</xml>");
        return sb.toString();
    }
}
