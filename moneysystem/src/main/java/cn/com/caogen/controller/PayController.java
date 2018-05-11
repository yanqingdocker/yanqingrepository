package cn.com.caogen.controller;

import cn.com.caogen.entity.Count;
import cn.com.caogen.externIsystem.service.PayService;
import cn.com.caogen.service.CountServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.ResponseMessage;
import cn.com.caogen.util.StringUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * author:huyanqing
 * Date:2018/4/23
 */
@RestController
@RequestMapping("/pay")
public class PayController {

    private static Logger logger = LoggerFactory.getLogger(PayController.class);

    private static Map<String, Object> backParm;
    @Autowired
    private CountServiceImpl countServiceImpl;

    @RequestMapping("/recharge")
    public String pay(@RequestParam("datas") String args,HttpServletRequest request) {
        logger.info("pay start: datas="+args);
        if (!StringUtil.checkStrs(args)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Lock lock = new ReentrantLock();
        lock.lock();//加锁
        try {
            JSONObject jsonObject = JSONObject.fromObject(args);
            // 交易金额
            String tradeMoney = jsonObject.get("tradeMoney").toString();
            // 支付方式
            String payType = jsonObject.get("payType").toString();
            Map<String, Object> map = new HashMap<String, Object>();
            //map.put("tradeMoney", Double.parseDouble(tradeMoney));
            map.put("tradeMoney", 1000);
            map.put("selfParam", "自定义参数");
            // 1000默认支持所有支付方式
          //  map.put("payType", payType);
            map.put("payType", 1000);
            map.put("appSence", null);
            map.put("syncURL", "http://47.104.233.0:8080/pay/goback");
            map.put("asynURL", "http://47.104.233.0:8080/pay/goback");

            backParm = new HashMap<String, Object>();
            backParm.put("blace", Double.parseDouble(tradeMoney));
            backParm.put("id", jsonObject.get("id"));
            backParm.put("sessionId",request.getSession().getId());
            PayService.orderPay(map);
        } catch (Exception e) {

        } finally {
            lock.unlock();//释放锁
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 支付后回调接口
     */
    @RequestMapping("/goback")
    @Transactional //开启事务，两个账户都更新成功是才算成功
    public String goBack(@RequestParam String sessionid) {
        logger.info("goBack start");
        Lock lock = new ReentrantLock();
        lock.lock();//加锁
        try {

            if (!backParm.isEmpty()) {
                if(!backParm.get("sessionId").equals(sessionid)){
                    return "无权";
                }
                String id = (String) backParm.get("id");
                double blance = (double) backParm.get("blance");
                Count personCount = countServiceImpl.queryById(id);
                personCount.setBlance(personCount.getBlance() + (double) backParm.get("blace"));
                //更新个人账户
                countServiceImpl.updateCount(id, personCount.getBlance() + blance, null,null);

                //更新系统账户
                //增加操作记录
                backParm.clear();//清空backParm
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
            }
        } catch (Exception e) {

        } finally {
            lock.unlock();
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
    }

}
