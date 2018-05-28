package cn.com.caogen.controller;

import cn.com.caogen.entity.Count;
import cn.com.caogen.entity.User;
import cn.com.caogen.externIsystem.service.MessageService;
import cn.com.caogen.service.CountServiceImpl;
import cn.com.caogen.service.ICountService;
import cn.com.caogen.service.IUserService;
import cn.com.caogen.util.*;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.swing.text.Document;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/4/19
 */
@RestController
@RequestMapping("/count")
public class CountController {

    private static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private CountServiceImpl countServiceImpl;

    @Autowired
    private IUserService userServiceImpl;

    private static String check_Num = "";

    private static String phone = "";

    /**
     * 创建账户
     *
     * @param countType
     * @return
     */
    @RequestMapping(path = "/createCount", method = RequestMethod.POST)
    public String createCount(@RequestParam("countType") String countType, @RequestParam("payPwd") String payPwd, HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("createCount start :countType=" + countType);

        if (StringUtil.checkStrs(countType)) {
            if (checkUser(request.getSession().getAttribute("phone").toString())) {
                payPwd = MD5Util.string2MD5(payPwd);
                return countServiceImpl.createCount(countType, payPwd, request.getSession().getAttribute("userid").toString());

            } else {
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.NOT_AUTHENTION)).toString();
            }
        } else {
            logger.error("startOrstopcount id or state is null;");
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }

    }

    /**
     * 修改账户状态
     *
     * @param id
     * @param state
     * @return
     */
    @RequestMapping(path = "/startOrstopCount", method = RequestMethod.POST)
    public String startOrstopcount(@RequestParam("id") String id, @RequestParam("state") String state,HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("startOrstopcount start: id="+id+" state="+state);
        if (StringUtil.checkStrs(id, state)) {
            return countServiceImpl.updateCount(id, 0, state,null);
        } else {
            logger.error("startOrstopcount id or state is null");
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }

    }



    /**
     * 查询单个账户
     *
     * @param id
     * @return
     */
    @RequestMapping(path = "/queryCount", method = RequestMethod.GET)
    public String queryCount(@RequestParam("id") String id) {

        logger.info("queryCount start ");

        return JSONObject.fromObject(countServiceImpl.queryById(id)).toString();
    }


    /**
     * 转账
     *
     * @param id
     * @param moneynum
     * @param receivecount
     * @param payPwd
     * @return
     */
    @RequestMapping(path = "/switch", method = RequestMethod.POST)
    public String countSwitch(HttpServletRequest request,@RequestParam("countid") String id, @RequestParam("moneynum") Double moneynum, @RequestParam("receivecount") String receivecount, @RequestParam("payPwd") String payPwd) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("countSwitch start: countid="+id+",moneynum="+moneynum+",receivecount="+receivecount+",payPaw="+payPwd);
        if (!StringUtil.checkStrs(id, String.valueOf(moneynum), receivecount)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }
        Count srccount = countServiceImpl.queryById(id);
        //校验支付密码
        payPwd = MD5Util.string2MD5(payPwd);
        if (!payPwd.equals(srccount.getPayPwd())) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.PAYPWDERROR)).toString();
        }
        //校验账户余额
        if (srccount.getBlance() < moneynum) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.NOTBLANCE)).toString();
        }
        User user = getUser(receivecount, null);
        if (user == null) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.PAYPWDERROR)).toString();
        }
        //校验对方账户
        List<Count> countList = countServiceImpl.queryByUserId(user.getUserid());
        Count destCount = null;
        for (Count tempcount : countList) {
            if (tempcount.getCountType().equals(srccount.getCountType())) {
                destCount = tempcount;
                break;
            }
        }
        if (destCount == null) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.NOTTYPECOUNT)).toString();

        }
        countServiceImpl.countswitch(srccount, destCount, moneynum);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();

    }

    /**
     * 兑换
     *
     * @param datas
     * @return
     */
    @RequestMapping(path = "/exchange", method = RequestMethod.POST)
    public String exchange(@RequestParam("datas") String datas,HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("exchange start: datas="+datas);
        if (!StringUtil.checkStrs(datas)) {
            return net.sf.json.JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }

        JSONObject jsonObject = JSONObject.fromObject(datas);
        String srccountid = jsonObject.getString("srcountid");
        String destcountid = jsonObject.getString("destcountid");
        Double srcmoney = jsonObject.getDouble("srcmoney");
        Double destmoney = jsonObject.getDouble("destmoney");
        String payPwd = jsonObject.getString("paypwd");
        payPwd = MD5Util.string2MD5(payPwd);
        if (!StringUtil.checkStrs(srccountid, destcountid, String.valueOf(srcmoney), String.valueOf(destmoney), payPwd)) {
            return net.sf.json.JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.ERROR_ARGS)).toString();
        }
        return countServiceImpl.exchange(srccountid, destcountid, srcmoney, destmoney, payPwd);

    }

    @RequestMapping(path = "/inMoney", method = RequestMethod.POST)
    public String inMoney(HttpServletRequest request,@RequestParam("telphone") String telphone,@RequestParam("type") String type,@RequestParam("num") String num){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        if(!StringUtil.checkStrs(telphone,type,num)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        User user = getUser(telphone, null);
        if(user==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTSRCORDEST)).toString();
        }
        List<Count> countList=countServiceImpl.queryByUserId(user.getUserid());
        if(countList.isEmpty()){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTSRCORDEST)).toString();
        }
        Count count=null;
        boolean flag=true;
        for(Count tempcount:countList){
            if(tempcount.getCountType().equals(type)){
                count=tempcount;
                flag=false;
                break;
            }
        }
        if(flag){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTTYPECOUNT)).toString();
        }
        count.setBlance(count.getBlance()+Double.parseDouble(num));
        countServiceImpl.updateCount(String.valueOf(count.getId()),count.getBlance(),null,null);
        String operuser="操作员-"+(String)request.getSession().getAttribute("username");
        countServiceImpl.saveOperaLog(count.getCardId(),count.getCountType(),Double.parseDouble(num),ConstantUtil.SERVICETYPE_INMONEY,operuser,ConstantUtil.MONEY_IN,IpUtil.getIpAddr(request));
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    @RequestMapping(path = "/outMoney", method = RequestMethod.POST)
    public String outMoney(HttpServletRequest request,@RequestParam("telphone") String telphone,@RequestParam("type") String type,@RequestParam("num") String num,@RequestParam("payPwd") String payPwd){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        if(!StringUtil.checkStrs(telphone,type,num,payPwd)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        User user = getUser(telphone, null);
        if(user==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTSRCORDEST)).toString();
        }
        List<Count> countList=countServiceImpl.queryByUserId(user.getUserid());
        if(countList.isEmpty()){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTSRCORDEST)).toString();
        }
        Count count=null;
        boolean flag=true;
        for(Count tempcount:countList){
            if(tempcount.getCountType().equals(type)){
                count=tempcount;
                flag=false;
                break;
            }
        }
        if(flag){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTTYPECOUNT)).toString();
        }
        if(!count.getPayPwd().equals( MD5Util.string2MD5(payPwd))){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.PAYPWDERROR)).toString();
        }
        if(count.getBlance()<Double.parseDouble(num)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.NOTBLANCE)).toString();
        }
        count.setBlance(count.getBlance()-Double.parseDouble(num));
        countServiceImpl.updateCount(String.valueOf(count.getId()),count.getBlance(),null,null);
        String operuser="操作员-"+(String)request.getSession().getAttribute("username");
        countServiceImpl.saveOperaLog(count.getCardId(),count.getCountType(),-Double.parseDouble(num),ConstantUtil.SERVICETYPE_OUTMONEY,operuser,ConstantUtil.MONEY_OUT,IpUtil.getIpAddr(request));

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }


    @RequestMapping(path = "/queryblancebyType", method = RequestMethod.GET)
    public String queryblancebyType() {
        logger.info("queryblancebyType start ");
        List<Map<String,Object>> list=countServiceImpl.queryblancebyType();
        return JSONArray.fromObject(list).toString();
    }

    /**
     * 查询系统中所有账户
     *
     * @return
     */
    @RequestMapping(path = "/queryAllCount", method = RequestMethod.GET)
    public String queryAllCount(HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("queryAllCount start ");
        return countServiceImpl.queryAll();
    }


    @RequestMapping(path="queryMoneyType",method = RequestMethod.GET)
    public String getMoneyType(HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        return JSONArray.fromObject(ConstantUtil.MONEY_TYPES).toString();
    }

    public boolean checkUser(String telphone) {
        logger.info("checkUser start: telphone="+telphone);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", telphone);
        User temp = null;
        List<User> userList = userServiceImpl.queryAll(map);
        if (userList.isEmpty()) {
            return false;
        } else {
            temp = userList.get(0);
        }
        if (temp.getIsauthentication() == 0) {
            return false;
        } else {
            return true;
        }
    }

    public User getUser(String telphone, String userid) {
        logger.info("getUser start: telphone="+telphone+",userid="+userid);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("phone", telphone);
        User temp = null;
        List<User> userList = userServiceImpl.queryAll(map);
        if (userList.isEmpty()) {
            return null;
        } else {
            return userList.get(0);
        }
    }

    /**
     * 发送手机验证码
     * @param telphone
     */
    @RequestMapping(path = "/checkPhone", method = RequestMethod.POST)
    public String checkPhone(@RequestParam("telphone") String telphone,HttpServletRequest request) {
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
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
}
