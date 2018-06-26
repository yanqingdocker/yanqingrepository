package cn.com.caogen.controller;

import cn.com.caogen.util.*;
import net.sf.json.JSON;
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
import java.util.*;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@RestController
@RequestMapping("/rate")
public class RateController {

    private static Logger logger = LoggerFactory.getLogger(RateController.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 获取汇率
     *
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryAll", method = RequestMethod.GET)
    public String getRates(HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("getRates start");
        List<String> list = new ArrayList<String>(7);
        list.add(stringRedisTemplate.opsForValue().get(ConstantUtil.ONE));
        list.add(stringRedisTemplate.opsForValue().get(ConstantUtil.TWO));
        list.add(stringRedisTemplate.opsForValue().get(ConstantUtil.THREE));
        list.add(stringRedisTemplate.opsForValue().get(ConstantUtil.FOURE));
        list.add(stringRedisTemplate.opsForValue().get(ConstantUtil.FIVE));
        list.add(stringRedisTemplate.opsForValue().get(ConstantUtil.SIX));
        list.add(stringRedisTemplate.opsForValue().get(ConstantUtil.SENVEN));
        return JSONArray.fromObject(list).toString();
    }

    /**
     * 获取IP
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/getIp", method = RequestMethod.GET)
    public String getIp(HttpServletRequest request) {
        return IpUtil.getIpAddr(request);
    }

    @RequestMapping(path = "/getSingleRate", method = RequestMethod.GET)
    public String getSingleRate(@RequestParam("type") String type){
        String rs=stringRedisTemplate.opsForValue().get(ConstantUtil.SENVEN);
        JSONObject jsonObject=JSONObject.fromObject(rs);
        String buyPid=jsonObject.getJSONObject(type).getString("buyPic");
        String sellPic=jsonObject.getJSONObject(type).getString("sellPic");
        StringBuffer sb=new StringBuffer();
        sb.append("{'buyPic':'").append(buyPid).append("','sellPic':'").append(sellPic).append("'}");

        return   JSONObject.fromObject(sb.toString()).toString();
    }

    /**
     * 修改买卖汇率
     * @param type
     * @param sellPic 卖出价
     * @param buyPic 买入价
     * @return
     */
    @RequestMapping(path = "/update",method = RequestMethod.POST)
    public String updateRate(HttpServletRequest request,@RequestParam("type") String type,@RequestParam("sellPic") String sellPic,@RequestParam("buyPic") String buyPic){
        logger.info("update rate start");
        if(!StringUtil.checkStrs(type,sellPic,buyPic)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        String rs=stringRedisTemplate.opsForValue().get(ConstantUtil.SENVEN);
        if(StringUtil.checkStrs(rs)){
            JSONObject jsonObject=JSONObject.fromObject(rs).getJSONObject(type);
            jsonObject.element("sellPic",sellPic);
            jsonObject.element("buyPic",buyPic);
            JSONObject result=JSONObject.fromObject(rs);
            result.element(type,jsonObject);


            stringRedisTemplate.opsForValue().set(ConstantUtil.SENVEN, result.toString());
        }else{
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }
}
