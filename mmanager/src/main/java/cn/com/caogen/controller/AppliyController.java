package cn.com.caogen.controller;

import cn.com.caogen.entity.Appliy;
import cn.com.caogen.service.AppliyServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import cn.com.caogen.util.ResponseMessage;
import net.sf.json.JSONArray;
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
