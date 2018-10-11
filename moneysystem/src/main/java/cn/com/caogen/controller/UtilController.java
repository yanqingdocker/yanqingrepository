package cn.com.caogen.controller;

import cn.com.caogen.util.ConstantUtil;
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
    @RequestMapping(path = "getUrl",method = RequestMethod.GET)
    public String upUrl(){
        logger.info("getUrl start:");
        String url=stringRedisTemplate.opsForValue().get("url");
        logger.info("getUrl start url=:"+url);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,url)).toString();
    }

}
