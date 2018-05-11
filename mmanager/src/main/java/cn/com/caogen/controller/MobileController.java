package cn.com.caogen.controller;

import cn.com.caogen.entity.Count;
import cn.com.caogen.externIsystem.service.MobileService;
import cn.com.caogen.service.ICountService;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.ResponseMessage;
import cn.com.caogen.util.StringUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/4/24
 */
@RestController
@RequestMapping("/mobile")
public class MobileController {

    private static Logger logger = LoggerFactory.getLogger(MobileController.class);

    @Autowired
    private ICountService countServiceImpl;

    /**
     * 话费充值
     * @param countId
     * @param cardNum
     * @param phone
     * @return
     */
    @RequestMapping(path = "/payMent", method = RequestMethod.POST)
    public String payMent(@RequestParam("countId") String countId,@RequestParam("cardNum") String cardNum,@RequestParam("phone") String phone) {
        logger.info("payMent start: countId="+countId+",cardNum="+cardNum+",phone="+phone);
        if (!StringUtil.checkStrs(countId,cardNum,phone)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        String oderid = String.valueOf(System.currentTimeMillis());
        int num=Integer.parseInt(cardNum);
        Count count=countServiceImpl.queryById(countId);
        if(count==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTSRCORDEST)).toString();
        }
        if(num>count.getBlance()){
            //金额不足
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTBLANCE)).toString();
        }
        try {
            String result = MobileService.onlineOrder(phone, num, oderid);
            if (StringUtils.isNotEmpty(result)) {
                JSONObject res = JSONObject.fromObject(result);
                if (ConstantUtil.MOBILE_SUCCESS_CODE.equals(res.get("reason"))) {
                    //充值提交成功,更新账户余额
                    count.setBlance(count.getBlance()-num);
                    countServiceImpl.updateCount(String.valueOf(count.getId()),count.getBlance(),null,null);
                } else {
                    return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
                }
            } else {
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.FAILSYSTEM)).toString();
            }
        } catch (Exception e) {

        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

}
