package com.hoaven.wechat.entity.message;

/**
 * 可通过Xml回复推送消息
 *
 */
public interface ReturnableXmlMessage {
    /**
     * passive response type
     * @return 
     */
    String toReturnXML();
}
