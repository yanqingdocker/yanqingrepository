package cn.com.caogen.weixin.entity;

import lombok.Setter;
import lombok.Getter;

/**
 * author:huyanqing
 * Date:2018/5/8
 */
@Setter
@Getter
public class Message {
    private String ToUserName;
    private String FromUserName;
    private long CreateTime;
    private String MsgType;
    private String MsgId;


}
