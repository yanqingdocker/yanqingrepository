package cn.com.caogen.controller;

import cn.com.caogen.entity.Loss;
import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Profits;
import cn.com.caogen.mapper.LossMapper;
import cn.com.caogen.service.LossServiceImpl;
import cn.com.caogen.service.ProfitsServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import cn.com.caogen.util.ResponseMessage;
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
 * Date:2018/5/8
 */
@RestController
@RequestMapping("/loss")
public class LossController {
    private static Logger logger = LoggerFactory.getLogger(LossController.class);
    @Autowired
    private LossServiceImpl lossService;


    @RequestMapping(path = "add",method = RequestMethod.POST)
    public String add(@RequestParam("projectname") String projectname,@RequestParam("moneytype") String moneytype,@RequestParam("num") Double num,@RequestParam("remark") String remark, HttpServletRequest request) throws Exception {
        logger.info("add start:");
        /*       if(!FilterAuthUtil.checkAuth(request)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
        }*/
        Loss loss=new Loss();
        loss.setProjectname(projectname);
        loss.setMoneytype(moneytype);
        loss.setCreattime(DateUtil.getTime());
        loss.setNum(num);
        loss.setRemark(remark);
        loss.setOperauser("admin");
        int rs=lossService.add(loss);
        if(rs==0){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.SYSTEMCOUNT_LESS)).toString();
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }




   @RequestMapping(path = "queryAll",method = RequestMethod.GET)
    public String queryAll(HttpServletRequest request) throws Exception {
    /*  if(!FilterAuthUtil.checkAuth(request)){
           return JSONObject.fromObject(new ResponseMessage(ConstantUtil.NO_AUTH,ConstantUtil.FAIL)).toString();
       }*/
        logger.info("queryAll start:");
        Map<String,Object> parmMap=new HashMap<String,Object>();
        List<Loss> losses=lossService.queryAll();
        return JSONArray.fromObject(losses).toString();
      }

}
