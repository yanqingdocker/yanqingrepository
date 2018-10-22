package cn.com.caogen.controller;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.entity.Task;
import cn.com.caogen.service.CashPoolServiceImpl;
import cn.com.caogen.service.CountServiceImpl;
import cn.com.caogen.service.OperaServiceImpl;
import cn.com.caogen.service.TaskServiceImpl;
import cn.com.caogen.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@RestController
@RequestMapping("/cashpool")
public class CashPoolController {
    private static Logger logger = LoggerFactory.getLogger(CashPoolController.class);
    @Autowired
    private CashPoolServiceImpl cashPoolService;
    @Autowired
    private OperaServiceImpl operaService;
    @Autowired
    private TaskServiceImpl taskService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 查询现金库
     * @return
     */
    @RequestMapping(path ="queryAll",method = RequestMethod.GET)
    public String queryAll(@RequestParam("servicebranch") String servicebranch, HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("queryAll start: servicebranch="+servicebranch);
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        List<CashPool> cashPoolList=cashPoolService.queryByType(null,servicebranch);
        return JSONArray.fromObject(cashPoolList).toString();
    }

    /**
     * 查询盈亏情况
     * @return
     */
    @RequestMapping(path ="queryProf",method = RequestMethod.GET)
    public String queryProf( HttpServletRequest request){

        logger.info("queryProf start:");
        Muser currentUser=(Muser) request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        String rs=stringRedisTemplate.opsForValue().get(ConstantUtil.SENVEN);
        JSONObject jsonObject=JSONObject.fromObject(rs);
        String buyPid=jsonObject.getJSONObject("USDCNY").getString("buyPic");
        Double buy=Double.parseDouble(buyPid);


        List<CashPool> cashPools=null;
        try {
            cashPools=(List)SerializeUtil.unserialize(JedisUtil.getJedis().get("cash".getBytes()));
        }catch (NullPointerException e){
            cashPools=(List)SerializeUtil.unserialize(JedisUtil.getJedis().get("cash".getBytes()));
        }
        List<CashPool> collection=new ArrayList<CashPool>();
        for(CashPool cashPool:cashPools){
            if(cashPool.getServicebranch().equals(currentUser.getServicebranch())){
                collection.add(cashPool);
            }
        }
        double num1,num2;
        CashPool cashPool=collection.get(0);
        if(cashPool.getCounttype().equals(ConstantUtil.MONEY_CNY)){
            num1=cashPool.getBlance()/buy;
        }else {
            num1=cashPool.getBlance();
        }

        cashPool=collection.get(1);
        if(cashPool.getCounttype().equals(ConstantUtil.MONEY_CNY)){
            num2=cashPool.getBlance()/buy;
        }else {
            num2=cashPool.getBlance();
        }
        double sum=num1+num2;


        CashPool USDPoolk=cashPoolService.queryByType(ConstantUtil.MONEY_USD,currentUser.getServicebranch()).get(0);
        CashPool CNYPoolk=cashPoolService.queryByType(ConstantUtil.MONEY_CNY,currentUser.getServicebranch()).get(0);
        double currentNum=USDPoolk.getBlance()+CNYPoolk.getBlance()/buy;
        if(currentNum-sum>0){
            //赚钱
            logger.info("赚取"+(currentNum-sum));
        }else  if(currentNum-sum==0) {
            //持平
            logger.info("持平");
        }else{
            //亏钱
            logger.info("亏损"+(currentNum-sum));
        }
        //插入数据库
        logger.info("queryProf end money="+(currentNum-sum));
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,String.valueOf(currentNum-sum))).toString();
    }

    /**
     * 现金库初始化
     * @param type
     * @param num
     * @param request
     * @return
     */
    @RequestMapping(path = "initCashPool",method = RequestMethod.POST)
    public String initCashPool(@RequestParam("type") String type,@RequestParam("num") double num,@RequestParam("oi") int oi, @RequestParam("branchname") String branchname,HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("initCashPool start:   type="+type+",num="+num+",oi="+oi);
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if(!StringUtil.checkStrs(type,String.valueOf(num),String.valueOf(oi))){
            logger.info(ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }

       CashPool cashPool=cashPoolService.queryByType(type,branchname).get(0);
        if (oi==ConstantUtil.MONEY_OUT){
            if(cashPool.getBlance()<num){
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.SYSTEMCOUNT_LESS)).toString();
            }
            cashPool.setBlance(cashPool.getBlance()-num);
        }
        if (oi==ConstantUtil.MONEY_IN){
            cashPool.setBlance(cashPool.getBlance()+num);
        }
       cashPool.setLasttime(DateUtil.getTime());
       cashPoolService.update(cashPool);
       return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 现金兑换
     * @param srccounttype
     * @param destcounttype
     * @param srcnum
     * @param destnum
     * @param request
     * @return
     */
    @RequestMapping(path = "exchange",method = RequestMethod.POST)
    public String exchange(@RequestParam("srccounttype") String srccounttype, @RequestParam("destcounttype") String destcounttype, @RequestParam("srcnum") Double srcnum, @RequestParam("destnum") Double destnum, @RequestParam("remark") String remark,@RequestParam("phone") String phone,@RequestParam("username") String username,@RequestParam("carduname") String carduname,@RequestParam("cardName") String cardName,@RequestParam("cardNum") String cardNum, @RequestParam("rate") String rate,HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("exchange start: srccounttype="+srccounttype+",destcounttype="+destcounttype+",srcnum="+srcnum+",destnum="+destnum+",remark="+remark+",phone="+phone+",username="+username+",carduname="+carduname+",cardName="+cardName+",cardNum="+cardNum);
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if(!ConstantUtil.SERVICE_BRANCH.equals(currentUser.getServicebranch())){
            int num=Integer.parseInt(DateUtil.getTime().split(" ")[1].split(":")[0]);
            logger.info("num="+num);
            if(9>num||num>18){
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_DATE)).toString();
            }
        }


        if(!StringUtil.checkStrs(srccounttype,destcounttype,String.valueOf(srcnum),String.valueOf(destnum),phone,username)){
            logger.info(ConstantUtil.ERROR_ARGS);
           return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }

        CashPool srcCashPool=cashPoolService.queryByType(srccounttype,currentUser.getServicebranch()).get(0);
        if(srcCashPool.getBlance()<srcnum){
            logger.info(ConstantUtil.SYSTEMCOUNT_LESS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.SYSTEMCOUNT_LESS)).toString();
        }

        String operuser="操作员-"+currentUser.getUsername();
        CashPool destCashPool=cashPoolService.queryByType(destcounttype,currentUser.getServicebranch()).get(0);
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("srccount",srcCashPool);
        parmMap.put("destcount",destCashPool);
        parmMap.put("srcnum",srcnum);
        parmMap.put("destnum",destnum);
        parmMap.put("ip",IpUtil.getIpAddr(request));
        parmMap.put("operauser",operuser);
        parmMap.put("servicebranch",currentUser.getServicebranch());
        parmMap.put("remark",remark);
        parmMap.put("phone",phone);
        parmMap.put("username",username);
        parmMap.put("snum",SerialnumberUtil.Getnum());
        parmMap.put("cardName",cardName);
        parmMap.put("carduname",carduname);
        parmMap.put("cardNum",cardNum);
        parmMap.put("rate",rate);
        cashPoolService.exchange(parmMap);
        Operation srcoperation=new Operation();
        srcoperation.setSnumber(parmMap.get("snum").toString());
        srcoperation.setOperaUser((String)parmMap.get("operauser"));
        srcoperation.setOperaType(ConstantUtil.MONEY_EXCHANGE);
        srcoperation.setCountType(destcounttype+"兑换"+srccounttype);
        srcoperation.setNum(-(Double)parmMap.get("srcnum"));
        srcoperation.setServicebranch((String)parmMap.get("servicebranch"));
        srcoperation.setPhone((String)parmMap.get("phone"));
        srcoperation.setUsername((String)parmMap.get("username"));
        srcoperation.setOperaTime(DateUtil.getDate());
//        if(!StringUtil.checkStrs(cardName,carduname,cardNum)){
//            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,JSONObject.fromObject(srcoperation).toString())).toString();
//
//        }
        Task task=new Task();
        task.setTaskname(ConstantUtil.MONEY_EXCHANGE);
        StringBuffer title=new StringBuffer();
        Muser muser=(Muser)request.getSession().getAttribute("currentUser");
        title.append(muser.getServicebranch()).append("网点发起兑换操作");
        title.append(";,币种:").append(srccounttype);
        title.append(";,开户行:").append(cardName);
        title.append(";,持卡人姓名:").append(carduname);
        title.append(";,银行卡号:").append(cardNum);
        title.append(";,金额:").append(String.valueOf(srcnum));
        title.append(";,汇率:").append(rate);
        task.setCreatetime(DateUtil.getTime());
        task.setState(ConstantUtil.TASK_UNDO);
        task.setTaskcontent(title.toString());
        task.setOperauser(muser.getUsername());
        task.setSnum(srcoperation.getSnumber());
        task.setRate(rate);
        taskService.addTask(task);

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS,JSONObject.fromObject(srcoperation).toString())).toString();

    }


    /**
     * 查询支出或收入美金
     * @return
     */
    @RequestMapping(path ="queryScope",method = RequestMethod.GET)
    public String queryScope(@RequestParam("branchname") String servicebranch,@RequestParam("starttime") String starttime,@RequestParam("endtime") String endtime, @RequestParam("type") String type,HttpServletRequest request){
      /*  if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }*/
        logger.info("queryAll start: branchname="+servicebranch+",starttime="+starttime+",endtime="+endtime+",type="+type);
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        logger.info("user=:"+currentUser.getUsername());
        if(!StringUtil.checkStrs(starttime,endtime,type)){
            logger.info(ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }

        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("starttime",starttime);
        parmMap.put("endtime",endtime);
        parmMap.put("type",type);

        if(StringUtil.checkStrs(servicebranch)){
            if(!"全部".equals(servicebranch)){
                parmMap.put("servicebranch",servicebranch);
            }

        }else{
            Muser muser=(Muser)request.getSession().getAttribute("currentUser");
            parmMap.put("servicebranch",muser.getServicebranch());
        }
        String rs=operaService.queryScope(parmMap);
        return rs;
    }



}
