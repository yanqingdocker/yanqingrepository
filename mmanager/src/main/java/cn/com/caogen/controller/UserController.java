package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.User;
import cn.com.caogen.externIsystem.service.IDCardService;
import cn.com.caogen.externIsystem.service.MessageService;
import cn.com.caogen.service.IUserService;
import cn.com.caogen.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    private static Map<String,HttpSession> sessionMap =new HashMap<String,HttpSession>();

    private static Map<String,HttpServletResponse> respMap =new HashMap<String,HttpServletResponse>();

    private static Map<Integer,String> userMap =new HashMap<Integer,String>();

    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static String check_Num = "";

    private static String phone = "";


    /**
     * 发送手机验证码
     * @param telphone
     */
    @RequestMapping(path = "/checkPhone", method = RequestMethod.POST)
    public String checkPhone(@RequestParam("telphone") String telphone) {
        logger.info("checkPhone start: telphone="+telphone);
        if (!StringUtil.checkStrs(telphone)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        int num = MessageService.checkPhone(telphone);
        if (num != 0) {
            phone=telphone;
            check_Num = String.valueOf(num);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }else{
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
    }


    /**
     * 查看所有会员
     * @return
     */
    @RequestMapping(path = "/queryAll", method = RequestMethod.GET)
    public String batchdelete() {
           logger.info("queryMuser start: ");
           List<User> users= userServiceImpl.queryAll(new HashMap<String,Object>());
           return JSONArray.fromObject(users).toString();

    }


    public User getUser(String telphone,String userid){
        logger.info("getUser start: telphone="+telphone+",userid="+userid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", telphone);
        User temp = null;
        List<User> userList=userServiceImpl.queryAll(map);
        if(userList.isEmpty()){
           return null;
        }else {
            return userList.get(0);
        }
    }

}

