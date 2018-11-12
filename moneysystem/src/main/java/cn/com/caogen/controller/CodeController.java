package cn.com.caogen.controller;

import cn.com.caogen.entity.User;

import cn.com.caogen.util.LoginUser;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.Base64;
import java.util.UUID;


@RestController
@RequestMapping("/code")
public class CodeController {
    private static Logger logger = LoggerFactory.getLogger(PageController.class);
    @RequestMapping(path="/check")
    public void LongConnectionCheck(HttpServletRequest request, HttpServletResponse response) throws Exception {
        PrintWriter out = response.getWriter();
        String uuid = request.getParameter("uuid");
        String jsonStr = "";
        logger.info("二维码检测  uuid="+uuid);
        long inTime=System.currentTimeMillis();
        Boolean bool = true;
        while (bool) {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //检测登录
            User userVo = LoginUser.getLoginUserMap().get(uuid);
            System.out.println("userVo:" + userVo);
            if(userVo != null){
                bool = false;
                jsonStr = "{\"telphone\":\""+userVo.getPhone()+"\",\"password\":\""+userVo.getPassword()+"\"}";
                JSONObject j=JSONObject.fromObject(jsonStr);
                logger.info("login ok : " + jsonStr);
                out.print(j);
                out.flush();
                out.close();
                LoginUser.getLoginUserMap().remove(uuid);
            }else{
                logger.info("login ok : 等待");
                if(System.currentTimeMillis() - inTime > 5000){
                    bool = false;
                }
            }
        }

    }

    @RequestMapping(path = "/getcode" )
    public void getCode(HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.info("login: getcode");
        PrintWriter out = response.getWriter();
        String uuid = UUID.randomUUID().toString();
        logger.info("code: uuid"+uuid);
        String  keyString="{\"key\":\"login\",\"uuid\":\""+uuid+"\"}";
        Base64.Encoder encoder =Base64.getEncoder();
        String  key=encoder.encodeToString(keyString.getBytes("UTF-8"));
        String jString = "{\"uuid\":\"" + uuid  + "\",\"key\":\""+key  +"\"}";
        out.print(JSONObject.fromObject(jString));
        out.flush();
        out.close();


    }



}
