package cn.com.caogen.controller;

import cn.com.caogen.entity.User;
import cn.com.caogen.externIsystem.service.AppliyService;
import cn.com.caogen.externIsystem.service.IDCardService;
import cn.com.caogen.externIsystem.service.MessageService;
import cn.com.caogen.service.IUserService;
import cn.com.caogen.util.*;
import net.sf.json.JSONObject;
import org.apache.catalina.servlet4preview.ServletContext;
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
import java.io.File;
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
    private static Map<String,HttpServletRequest> reqMap =new HashMap<String,HttpServletRequest>();

    private static Map<Integer,String> userMap =new HashMap<Integer,String>();

    @Autowired
    private IUserService userServiceImpl;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    private static String check_Num = "";

    private static String phone = "";

    @RequestMapping("/hello")
    public void hello() throws Exception {


    }

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
     * 用户注册
     * @param telphone
     * @param password
     * @param checkNum
     * @return
     */
    @RequestMapping(path = "/register", method = RequestMethod.POST)
    public String register(@RequestParam("telphone") String telphone, @RequestParam("password") String password, @RequestParam("checkNum") String checkNum) {
        logger.info("register start: telphone="+telphone+",checkNum="+checkNum);
        if (!StringUtil.checkStrs(telphone,password,checkNum)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        if (!StringUtils.isEmpty(checkNum) && check_Num.equals(checkNum)) {
            if(!phone.equals(telphone)){
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOT_EQUAL_PHONE)).toString();
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("phone", telphone);
            User temp = null;
            List<User> list=userServiceImpl.queryAll(map);
            if(!list.isEmpty()){
                    temp=list.get(0);
            }
            if(temp!=null){
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ALREADY_USER)).toString();
            }
            password = MD5Util.string2MD5(password);
            User user = new User();
            user.setPassword(password);
            user.setPhone(telphone);
            user.setCreatetime(DateUtil.getTime());
            userServiceImpl.addUser(user);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        } else {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.CHECKERROR_NUM)).toString();
        }
    }

    /**
     * 用户登录
     * @param telphone
     * @param password
     * @param request
     * @return
     */
    @RequestMapping(path="/login",method = RequestMethod.POST)
    public String login( @RequestParam("telphone") String telphone, @RequestParam("password") String password,HttpServletRequest request,HttpServletResponse response) throws Exception{
        logger.info("login: telphone="+telphone);
            if (!StringUtils.isEmpty(telphone) && !StringUtils.isEmpty(password)){
                password = MD5Util.string2MD5(password);
                User user=getUser(telphone,null);
                if(user==null){

                   return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.EROOR_USER)).toString();
                }
                if(request.getSession().getAttribute("userid")!=null){
                   return JSONObject.fromObject(user).toString();
                }
                if (password.equals(user.getPassword())) {
                    request.getSession().setAttribute("phone",user.getPhone());
                    request.getSession().setAttribute("userid",user.getUserid());
                    request.getSession().setAttribute("id",user.getUserid());
                    request.getSession().setAttribute("username",user.getUsername());
                    request.getSession().setMaxInactiveInterval(180);
                    checkSession(user.getUserid(),request,response);
                    return JSONObject.fromObject(user).toString();

                }else{
                    return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.EROOR_USER)).toString();
                }
            }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.EROOR_USER)).toString();
    }

    /**
     * 找回密码
     * @param checknum
     * @return
     */
    @RequestMapping(path="/findpsw",method = RequestMethod.POST)
    public String findpsw(@RequestParam("checknum") String checknum,@RequestParam("telphone") String telphone) {
        logger.info("findpsw start: checknum="+checknum+",telphone="+telphone);
        if (!StringUtil.checkStrs(checknum,telphone)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        if(getUser(telphone,null)==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.NOTFOUND_USER)).toString();
        }

        if (!StringUtils.isEmpty(checknum) && check_Num.equals(checknum)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }else{
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.CHECKERROR_NUM)).toString();
        }
    }

    /**
     * 重置密码
     * @param telphone
     * @param password
     * @return
     */
    @RequestMapping(path="/resetpwd",method = RequestMethod.POST)
    public String resetpwd(@RequestParam("telphone") String telphone,@RequestParam("password") String password) {
        logger.info("resetpwd start: telphone="+telphone+",password="+password);
        if (!StringUtil.checkStrs(telphone,password)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        password = MD5Util.string2MD5(password);
        User user=getUser(telphone,null);
        if(user==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
        user.setPassword(password);
        user.setLasttime(DateUtil.getTime());
        userServiceImpl.update(user);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 登录后重置密码
     * @param telphone
     * @param password
     * @return
     */
    @RequestMapping(path="/loginResetpwd",method = RequestMethod.POST)
    public String loginResetpwd(@RequestParam("telphone") String telphone,@RequestParam("checkNum") String checkNum,@RequestParam("password") String password) {
        logger.info("resetpwd start: telphone="+telphone+",password="+password);
        if (!StringUtil.checkStrs(telphone,password)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        if(!check_Num.equals(checkNum)||!phone.equals(telphone)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.CHECKERROR_NUM)).toString();
        }
        password = MD5Util.string2MD5(password);
        User user=getUser(telphone,null);
        if(user==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
        user.setPassword(password);
        user.setLasttime(DateUtil.getTime());
        userServiceImpl.update(user);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request) {
        logger.info("logout start");
        sessionMap.remove(request.getSession().getId());
        userMap.remove(request.getSession().getAttribute("userid"));
        request.getSession().invalidate();


        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 获取当前用户
     * @param request
     * @return
     */
    @RequestMapping("/getuser")
    public String getuser(HttpServletRequest request) {
        String telphone="";
        if(request.getSession().getAttribute("phone")!=null){
            telphone=request.getSession().getAttribute("phone").toString();
        }
        User user=getUser(telphone,null);
        if(user!=null){
            return JSONObject.fromObject(user).toString();
        }
        return null;
    }


    /**
     * 实名认证
     * @param datas
     * @return
     */
    @RequestMapping(path="/authentication",method =RequestMethod.POST)
    public String getuser(@RequestParam("datas") String datas,HttpServletRequest request) {
        logger.info("getuser start: datas="+datas);
        if (!StringUtil.checkStrs(datas)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        JSONObject jsonObject=JSONObject.fromObject(datas);
        String username=jsonObject.getString("username");
        String idcard=jsonObject.getString("idcard");
        String birthday=jsonObject.getString("birthday");
        String email=jsonObject.getString("email");
        String address=jsonObject.getString("address");
        String phone=(String)request.getSession().getAttribute("phone");
        User user=getUser(phone,null);
        if(IDCardService.authentication(username,idcard)){
            user.setUsername(username);
            user.setLasttime(DateUtil.getTime());
            user.setBirthday(birthday);
            user.setEmail(email);
            user.setIdcard(idcard);
            user.setAddress(address);
            user.setIsauthentication(1);

            userServiceImpl.update(user);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
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

    /**
     * 校验同一个账号只能在一处登录
     * @param userid
     * @param request
     */
    public void checkSession(int userid,HttpServletRequest request,HttpServletResponse response){
        HttpSession session=request.getSession();
        Iterator<Map.Entry<Integer, String>> iterator = userMap.entrySet().iterator();
            boolean flag=true;
        while(iterator.hasNext()){
            Map.Entry<Integer, String> entry=iterator.next();
            int tempid=entry.getKey();
            if(tempid==userid){

               if(session.getId().equals(entry.getValue())){
                   return;
               }

                //设置上一个相同账号的seesion失效
                if(reqMap.get(entry.getValue())!=null){
                    //如果上个未过期则另其过期
                    if(reqMap.get(entry.getValue()).getSession(true)==null){

                        try {
                            sessionMap.get(entry.getValue()).invalidate();
                            int i=5;
                        }catch (IllegalStateException e){
                            logger.warn("login invalidate error");
                        }

                    }
                }
                //移除上一个相同账号的session
                sessionMap.remove(entry.getValue());
                //放入新的session
                userMap.put(userid,session.getId());
                sessionMap.put(session.getId(),session);
                reqMap.put(session.getId(),request);
                flag=false;
                break;
            }
        }
        if(flag){
            userMap.put(userid,session.getId());
            sessionMap.put(session.getId(),session);
            reqMap.put(session.getId(),request);
        }
    }

}

