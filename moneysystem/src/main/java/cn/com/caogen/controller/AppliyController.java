package cn.com.caogen.controller;

import cn.com.caogen.entity.Appliy;
import cn.com.caogen.externIsystem.service.AppliyService;
import cn.com.caogen.service.AppliyServiceImpl;
import cn.com.caogen.service.IAppliyService;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import cn.com.caogen.util.ImageUtil;
import cn.com.caogen.util.ResponseMessage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.omg.PortableInterceptor.InterceptorOperations;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/8
 */
@RestController
@RequestMapping("/appliy")
public class AppliyController {
    private static Logger logger = LoggerFactory.getLogger(AppliyController.class);
    @Autowired
    private AppliyServiceImpl appliyService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private cn.com.caogen.service.AppliyService extappliyService;

    /**
     * 返回用户登录的二维码
     * @param request
     * @return
     */
    @RequestMapping(path = "scanlogin",method = RequestMethod.GET)
    public String scanLogin(HttpServletRequest request){
        int userid=(int)request.getSession().getAttribute("userid");

        extappliyService.init();
        String loginbase64= extappliyService.scanLogin(String.valueOf(userid));
        if(stringRedisTemplate.opsForValue().get(userid+"_loginbase64")!=null&&"".equals(loginbase64)){
            return stringRedisTemplate.opsForValue().get(userid+"_loginbase64");
        }else if(!"".equals(loginbase64)){
            stringRedisTemplate.opsForValue().set(userid+"_loginbase64",loginbase64);

        }
        return loginbase64;
    }

    /**
     * 返回用户允许PC端操作的二维码
     * @param request
     * @return
     */
    @RequestMapping(path = "scanAllow",method = RequestMethod.GET)
    public String scanAllow(HttpServletRequest request){
        try {
            Thread.sleep(5000);
        }catch (Exception e){

        }
        int userid=(int)request.getSession().getAttribute("userid");
        String allowbase64= extappliyService.scanAllow(String.valueOf(userid));
        if(stringRedisTemplate.opsForValue().get(userid+"_allowbase64")!=null&&"".equals(allowbase64)){
            return stringRedisTemplate.opsForValue().get(userid+"_allowbase64");
        }else if(!"".equals(allowbase64)){
            stringRedisTemplate.opsForValue().set(userid+"_allowbase64",allowbase64);

        }
        return allowbase64;
    }
    //绑定支付宝账号
    @RequestMapping(path = "bind",method = RequestMethod.GET)
    public String bindAppliy(HttpServletRequest request){
        int userid=(int)request.getSession().getAttribute("userid");
        try {
            try {
                Thread.sleep(5000);
            }catch (Exception e){

            }
            JSONObject jsonObject=extappliyService.getResult();
            if(jsonObject==null){
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
            }
            String real_name=jsonObject.getString("real_name");
            String alipay_account=jsonObject.getString("alipay_account");
            logger.info("real_name=："+real_name+"alipay_account: "+alipay_account);
            Map<String,Object> parmMap=new HashMap<String,Object>();
            List<Appliy> appliyList=appliyService.query(new HashMap<String,Object>());
            for(Appliy appliy:appliyList){
                if(appliy.getAppliycount().equals(alipay_account)){
                    return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ALREADY_APPLIY_COUNT)).toString();
                }
            }
            //保存
            Appliy appliy=new Appliy();
            appliy.setRealname(real_name);
            appliy.setAppliycount(alipay_account);
            appliy.setUserid((int)request.getSession().getAttribute("userid"));
            appliy.setCreatetime(DateUtil.getDate());
            appliyService.bind(appliy);
            stringRedisTemplate.delete(userid+"_loginbase64");
            stringRedisTemplate.delete(userid+"_allowbase64");

        }catch (Exception e){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 解绑支付宝账号
     * @param id
     * @return
     */
    @RequestMapping(path = "unbindAppliy",method = RequestMethod.POST)
    public String unbindAppliy(@RequestParam("id") String id){
        appliyService.unbind(Integer.parseInt(id));
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 查询所有支付宝账号
     * @return
     */
    @RequestMapping(path = "queryAll",method = RequestMethod.GET)
    public String queryAll(){
        Map<String,Object> parmMap=new HashMap<String,Object>();
        List<Appliy> appliyList=appliyService.query(new HashMap<String,Object>());
        return JSONArray.fromObject(appliyList).toString();
    }

    /**
     * 查询当前用户下的账号
     * @param request
     * @return
     */
    @RequestMapping(path = "querybyUserid",method = RequestMethod.GET)
    public String querybyUserid(HttpServletRequest request){
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("userid",request.getSession().getAttribute("userid"));
        List<Appliy> appliyList=appliyService.query(new HashMap<String,Object>());
        return JSONArray.fromObject(appliyList).toString();
    }
}