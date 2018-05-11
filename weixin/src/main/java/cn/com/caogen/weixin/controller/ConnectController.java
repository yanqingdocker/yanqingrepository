package cn.com.caogen.weixin.controller;

import cn.com.caogen.weixin.entity.Message;
import cn.com.caogen.weixin.entity.TextMessage;
import cn.com.caogen.weixin.util.MessageUtil;
import cn.com.caogen.weixin.util.SHA1;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/8
 */
@RestController
public class ConnectController {
    private final static String TOKEN="hdocker";
    @RequestMapping(path = "/connection",method = RequestMethod.GET)
    public String connection(HttpServletRequest request){

        String signature=request.getParameter("signature");
        String timestamp=request.getParameter("timestamp");
        String nonce=request.getParameter("nonce");
        String echostr=request.getParameter("echostr");
        String[] strings=new String[]{"docker",timestamp,nonce};

        String rs=SHA1.getSHA1(TOKEN,timestamp,nonce);
        if(rs.equals(signature)){
            return echostr;
        }else {
            return null;
        }


    }

    @RequestMapping(path = "/connection",method = RequestMethod.POST)
    public String message(HttpServletRequest request, HttpServletResponse response) throws Exception{
        /*request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        PrintWriter printWriter=response.getWriter();*/
        Map<String,String> map=MessageUtil.XmlToMap(request);
         String toUserName=map.get("ToUserName");
         String fromUserName=map.get("FromUserName");
         String msgType=map.get("MsgType");
         String msgId=map.get("MsgId");
        String content=map.get("Content");
        String xml=null;
        if("text".equals(msgType)){
              TextMessage message=new TextMessage();

              message.setToUserName(fromUserName);
              message.setFromUserName(toUserName);
              message.setCreateTime(System.currentTimeMillis());
              message.setMsgType("text");
              message.setContent("你好，已经接收到:"+content);
              xml=MessageUtil.textMessageXml(message);
        }
        System.out.print(xml);
        return xml;

    }


    @RequestMapping(path = "/test",method = RequestMethod.POST)
    public String message(){
        System.out.print("=========================");
        return "xml";

    }
}
