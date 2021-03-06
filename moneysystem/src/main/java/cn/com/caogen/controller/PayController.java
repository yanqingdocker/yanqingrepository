package cn.com.caogen.controller;

import cn.com.caogen.cash.serevice.PayforService;
import cn.com.caogen.entity.BankCard;
import cn.com.caogen.entity.Count;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.externIsystem.mode.HttpSendResult;
import cn.com.caogen.externIsystem.service.PayService;
import cn.com.caogen.externIsystem.util.*;
import cn.com.caogen.service.BankCardServiceImpl;
import cn.com.caogen.service.CountServiceImpl;
import cn.com.caogen.service.OperaServiceImpl;
import cn.com.caogen.util.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @Autowired
    private OperaServiceImpl operaService;
    @Autowired
    private CountServiceImpl countServiceImpl;
    @Autowired
    private BankCardServiceImpl bankCardService;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    private int userid;

    private static final String CHAR_SET = "utf-8";
    public static final String PAY_URL = "http://business.goldcoinpay.com/api/merchantWithdraw";
    @RequestMapping(value = "/recharge",method = RequestMethod.POST)
    public void pay( @RequestParam("tradeMoney") String tradeMoney,@RequestParam("cardid") String cardid,HttpServletResponse response,HttpServletRequest request) {
        //@RequestParam("datas") String datas,

//        Lock lock = new ReentrantLock();
//        lock.lock();//加锁
        String msg="";
        try {

            String blance=String.valueOf(Integer.parseInt(tradeMoney)*100);
            Double money= Double.parseDouble(tradeMoney);
            Map<String,String> map=new HashMap<String, String>();
            map.put("paymentName", "AlipayScan");
            map.put("sence", "1001");
            map.put("tradeMoney", blance);
            map.put("loginId", "491570696611168256");
            map.put("merOrdid", System.currentTimeMillis()+"");
            map.put("orderName", "ceshi");
            String userid=String.valueOf(JedisUtil.getUser(request).getUserid());
            map.put("asynNotificationUrl", "http://47.75.105.71:443/goback?userid="+userid+"&&cardid="+cardid+"&&blance="+blance);
            map.put("syncNotificationUrl", "http://47.75.105.71:443/goback?userid="+userid+"&&cardid="+cardid+"&&blance="+blance);
            msg=PayService.orderPay(map);
            response.setCharacterEncoding("UTF-8");
            response.setContentType("text/html;charset=UTF-8");
            response.getWriter().print(msg);
        } catch (Exception e) {

        }
// finally {
//            lock.unlock();//释放锁
//        }
    }




    /**
     * 提现接口
     */
    @RequestMapping("/getcash")
    @Transactional
    public String cash(@RequestParam("datas") String datas,HttpServletRequest request){
        logger.info("cash bigin");
        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("countswitch");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status=transactionManager.getTransaction(def);
        String currencyName = "CNY";
        String id = "" + System.currentTimeMillis();
        try {
            JSONObject jsonObject = JSONObject.fromObject(datas);

            String countid=jsonObject.getString("countid");
            String tradeMoney = jsonObject.getString("tradeMoney");//转账金额
            String a1 = String.valueOf(Integer.parseInt(tradeMoney)*100);
            Count count=countServiceImpl.queryById(countid);
            if(count==null){
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
            }
            if(count.getBlance()<Double.parseDouble(tradeMoney)){
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTBLANCE)).toString();
            }
            String bankid = jsonObject.get("bankid").toString();
            Map<String,Object> parmMap=new HashMap<String,Object>();
            parmMap.put("id",bankid);
            BankCard bankCard=bankCardService.queryCondition(parmMap).get(0);
            String cardHolder = bankCard.getUsername();//持卡人姓名
            String cardNo = bankCard.getBankCard();//卡号
            String cardtype=bankCard.getBankType();

            StringBuffer sb=new StringBuffer(24);
            for(int i=0;i<24;i++){
                int a=(int)(Math.random()*10);

                sb.append(a);
            }

            Map<String, String> dataMap = new HashMap<String, String>();
            String LOGINID="491570696611168256";
            dataMap.put("loginId", "491570696611168256");
            dataMap.put("receivableAccount", cardNo);
            dataMap.put("receivableMan", cardHolder);
            dataMap.put("tradeMoney", a1);
            dataMap.put("businessId",sb.toString());
            dataMap.put("bankName",cardtype);
            dataMap.put("withdrawType", "1001");
            String json  = GsonUtil.toJson(dataMap);
            Map<String, String> param = getRequestMap(json, RSAUtils.serverPublic, RSAUtils.merprivkey, LOGINID);
            SimpleHttpsClient httpClient= new SimpleHttpsClient();
            HttpSendResult result  = null;
            try{
                result  = httpClient.postRequest(PAY_URL, param, 30*000);
                System.out.println(result);
                System.out.println(result.getStatus());
                if(result==null){
                    logger.info("提现失败");
                }

            }catch  (Exception e){
                logger.info("提现失败");
            }
            try {
                Double num=Double.parseDouble(tradeMoney);
                logger.info("更新余额");
                countServiceImpl.updateCount(String.valueOf(count.getId()), count.getBlance() - Double.parseDouble(tradeMoney), null,null);
                 Operation operation=new Operation();
                operation.setOperaType(ConstantUtil.SERVICETYPE_DEPOSIT);
                operation.setCountid(count.getCardId());
                operation.setSnumber(SerialnumberUtil.Getnum());
                operation.setOi(ConstantUtil.MONEY_OUT);
                operation.setCountType(count.getCountType());
                operation.setServicebranch(ConstantUtil.SYSTEM);
                operation.setNum(Double.parseDouble(tradeMoney));
                operation.setOperaTime(DateUtil.getTime());
                operation.setOperaIp(IpUtil.getIpAddr(request));
                operaService.add(operation);

            }catch (Exception e)  {
                logger.info("更新余额失败");
            }
        } catch (Exception e) {
            logger.info("cash fail");
            transactionManager.rollback(status);
        } finally {
        }
        logger.info("cash success");
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }


    /**
     * 组装请求参数
     */
    public static Map<String, String> getRequestMap(String jsonParam,String serverPublic,String privateKey,String logid) throws Exception{
        if (StringUtils.isEmpty(jsonParam) || StringUtils.isEmpty(serverPublic) || StringUtils.isEmpty(privateKey)
                || StringUtils.isEmpty(logid)) {
            return null;
        }


        byte by[] = SecurityRSAPay.encryptByPublicKey(jsonParam.getBytes(CHAR_SET), Base64Local.decode(serverPublic));
        String baseStrDec = Base64Local.encodeToString(by, true);

        //商户自己的私钥签名
        byte signBy[] = SecurityRSAPay.sign(by, Base64Local.decode(privateKey));
        String sign = Base64Local.encodeToString(signBy, true);

        //组装请求参数
        Map<String,String>  map = new HashMap<String, String>();

        map.put("data", baseStrDec);
        map.put("sign", sign);
        map.put("loginId", logid);
        map.put("signType", "RSA");

        return map;
    }




































//    @RequestMapping("/recharge")
//    public void pay( @RequestParam("tradeMoney") String tradeMoney, @RequestParam("cardid") String cardid,HttpServletResponse response,HttpServletRequest request) {
//        //@RequestParam("datas") String datas,
//
//        Lock lock = new ReentrantLock();
//        lock.lock();//加锁
//        String msg="";
//        try {
//            String blance=String.valueOf(Integer.parseInt(tradeMoney)*100);
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("blance", blance);
//            map.put("cardid",cardid );
//            map.put("userid",JedisUtil.getUser(request).getUserid());
//            map.put("sessionid",request.getSession().getId());
//            userid=JedisUtil.getUser(request).getUserid();
//            msg=PayService.orderPay(map);
//            response.setContentType("text/html");
//            response.getWriter().print(msg);
//        } catch (Exception e) {
//
//        } finally {
//            lock.unlock();//释放锁
//        }
//    }
//
//    /**
//     * 提现接口
//     */
//    @RequestMapping("/getcash")
//    @Transactional
//    public String cash(@RequestParam("datas") String datas,HttpServletRequest request){
//        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
//        def.setName("countswitch");
//        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
//        TransactionStatus status=transactionManager.getTransaction(def);
//        String currencyName = "CNY";
//        String id = "" + System.currentTimeMillis();
//        try {
//            JSONObject jsonObject = JSONObject.fromObject(datas);
//
//            String countid=jsonObject.getString("countid");
//            String tradeMoney = jsonObject.getString("tradeMoney");//转账金额
//            Count count=countServiceImpl.queryById(countid);
//            if(count==null){
//                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
//            }
//            if(count.getBlance()<Double.parseDouble(tradeMoney)){
//                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTBLANCE)).toString();
//            }
//            String bankid = jsonObject.get("bankid").toString();
//            Map<String,Object> parmMap=new HashMap<String,Object>();
//            parmMap.put("id",bankid);
//            BankCard bankCard=bankCardService.queryCondition(parmMap).get(0);
//            String cardHolder = bankCard.getUsername();//持卡人姓名
//            String cardNo = bankCard.getBankCard();//卡号
//
////            String re  = PayforService.singlePenTransfer(id, cardHolder, cardNo, String.valueOf(tradeMoney), "",null,currencyName,"","","");
////            JSONObject jsonObject1=JSONObject.fromObject(re);
////            if(!"0000".equalsIgnoreCase(jsonObject1.getString("respCode"))){
////                logger.info("cash fail");
////                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
////            }
//            logger.info("开始");
//            try {
//
//
//                countServiceImpl.updateCount(String.valueOf(count.getId()), count.getBlance() - Double.parseDouble(tradeMoney), null, null);
//                Operation operation=new Operation();
//                operation.setOperaType(ConstantUtil.SERVICETYPE_DEPOSIT);
//                operation.setCountid(count.getCardId());
//                operation.setSnumber(SerialnumberUtil.Getnum());
//                operation.setOi(ConstantUtil.MONEY_OUT);
//                operation.setCountType(count.getCountType());
//                operation.setServicebranch(ConstantUtil.SYSTEM);
//                operation.setNum(Double.parseDouble(tradeMoney));
//                operation.setOperaTime(DateUtil.getTime());
//                operation.setOperaIp(IpUtil.getIpAddr(request));
//                operaService.add(operation);
//                logger.info("成功");
//            }catch (Exception E){
//                logger.info("SHIBAI ");
//            }
//
//
//        } catch (Exception e) {
//            logger.info("cash fail");
//            transactionManager.rollback(status);
//        } finally {
//
//        }
//        logger.info("cash success");
//        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
//    }


}
