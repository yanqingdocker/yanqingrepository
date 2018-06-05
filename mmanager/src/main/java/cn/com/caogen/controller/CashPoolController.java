package cn.com.caogen.controller;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.service.CashPoolServiceImpl;
import cn.com.caogen.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
 * Date:2018/5/17
 */
@RestController
@RequestMapping("/cashpool")
public class CashPoolController {
    private static Logger logger = LoggerFactory.getLogger(CashPoolController.class);
    @Autowired
    private CashPoolServiceImpl cashPoolService;

    /**
     * 查询现金库
     * @return
     */
    @RequestMapping(path ="queryAll",method = RequestMethod.GET)
    public String queryAll(@RequestParam("servicebranch") String servicebranch, HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("queryAll start:");
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
        List<CashPool> cashPoolList=cashPoolService.queryByType(null,servicebranch);
        return JSONArray.fromObject(cashPoolList).toString();
    }

    /**
     * 现金库初始化
     * @param type
     * @param num
     * @param request
     * @return
     */
    @RequestMapping(path = "initCashPool",method = RequestMethod.POST)
    public String initCashPool(@RequestParam("type") String type,@RequestParam("num") double num,@RequestParam("oi") int oi, HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        if(!StringUtil.checkStrs(type,String.valueOf(num),String.valueOf(oi))){
            logger.info(ConstantUtil.ERROR_ARGS);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
       CashPool cashPool=cashPoolService.queryByType(type,currentUser.getServicebranch()).get(0);
        if (oi==ConstantUtil.MONEY_OUT){
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
    public String exchange(@RequestParam("srccounttype") String srccounttype, @RequestParam("destcounttype") String destcounttype, @RequestParam("srcnum") Double srcnum, @RequestParam("destnum") Double destnum, HttpServletRequest request){
        if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }
        logger.info("exchange start:");
        if(!StringUtil.checkStrs(srccounttype,destcounttype,String.valueOf(srcnum),String.valueOf(destnum))){
            logger.info(ConstantUtil.ERROR_ARGS);
           return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Muser currentUser=(Muser)request.getSession().getAttribute("currentUser");
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
        parmMap.put("srcnum",destnum);
        parmMap.put("destnum",srcnum);
        parmMap.put("ip",IpUtil.getIpAddr(request));
        parmMap.put("operauser",operuser);
        parmMap.put("servicebranch",currentUser.getServicebranch());
        cashPoolService.exchange(parmMap);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();

    }
}
