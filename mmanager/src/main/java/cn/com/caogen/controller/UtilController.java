package cn.com.caogen.controller;

import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.FilterAuthUtil;
import cn.com.caogen.util.ResponseMessage;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * author:huyanqing
 * Date:2018/10/11
 */
@RestController
@RequestMapping("/util")
public class UtilController {
    private static Logger logger = LoggerFactory.getLogger(UtilController.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @RequestMapping(path = "upUrl",method = RequestMethod.POST)
    public String upUrl(HttpServletRequest request, @RequestParam("url") String url){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("upUrl start:   url="+url);
        stringRedisTemplate.opsForValue().set("url",url);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,null)).toString();
    }

    @RequestMapping(path = "upContent",method = RequestMethod.POST)
    public String upContent(HttpServletRequest request, @RequestParam("content") String content){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("upUrl start:   content="+content);
        stringRedisTemplate.opsForValue().set("content",content);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,null)).toString();
    }

}
