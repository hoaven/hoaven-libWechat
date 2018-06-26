package com.hoaven.wechat.entity.message.event;


import com.hoaven.wechat.entity.message.EventMessage;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by peter.wu on 2015/5/10 0010.
 */
@Getter
@Setter
public class KfSessionEventMessage extends EventMessage {
    private String kfAcount;

    private String fromKfAccount;
    private String toKfAccount;
}
