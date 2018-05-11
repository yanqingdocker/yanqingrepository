package cn.com.caogen.controller;

import cn.com.caogen.entity.Operation;
import cn.com.caogen.entity.User;
import cn.com.caogen.service.IOperaService;
import cn.com.caogen.service.UserServiceImpl;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import cn.com.caogen.util.IpUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/10
 */
@RestController
@RequestMapping("/opera")
public class OperaController {

    private static Logger logger = LoggerFactory.getLogger(OperaController.class);
    @Autowired
    private IOperaService operaServiceimpl;
    @Autowired
    private UserServiceImpl userServiceimpl;
    @RequestMapping(path = "/queryContition",method = RequestMethod.GET)
    public String queryContition(HttpServletRequest request){
        Operation operation=new Operation();
        operation.setCountid("13246465");
        operation.setNum(10.0);
        operation.setOperaTime(DateUtil.getTime());
        operation.setOperaType("充值");
        operation.setOi(1);
        operation.setOperaIp(IpUtil.getIpAddr(request));
        operation.setOperaUser("yanqing");
        operation.setCountType("CNY");
        operaServiceimpl.add(operation);
        return "opera";
    }

    /**
     * 查询所有操作记录
     * @return
     */
    @RequestMapping(path = "/queryAll",method = RequestMethod.GET)
    public String queryAll(){

        return JSONArray.fromObject(operaServiceimpl.queryAll()).toString();
    }

    /**
     * 查询当前用户下的账户的所有操作纪记录
     * @param request
     * @return
     */
    @RequestMapping(path = "/queryByUserid",method = RequestMethod.GET)
    public String queryByUserid(HttpServletRequest request){
        User user=userServiceimpl.querybyId((int)request.getSession().getAttribute("userid"));
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("operauser",user.getUserid());
        return JSONArray.fromObject(operaServiceimpl.queryAll(parmMap)).toString();
    }

    /**
     * 查询当天账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByDay",method = RequestMethod.GET)
    public String queryByDay(){


        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_TODAY);
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }


    /**
     * 查询本周账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByWeek",method = RequestMethod.GET)
    public String queryByWeek(){
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_WEEK);
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }

    /**
     * 查询本月账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByMonth",method = RequestMethod.GET)
    public String queryByMonth(){
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_MONTH);
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }

    /**
     * 查询本季度账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByquarter",method = RequestMethod.GET)
    public String queryByquarter(){
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_QUARTER);
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }

    /**
     * 查询本年账户的所有记录
     * @return
     */
    @RequestMapping(path = "/queryByYear",method = RequestMethod.GET)
    public String queryByYear(){
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put(ConstantUtil.QUERYDATE,ConstantUtil.DATE_YEAR);
        return JSONArray.fromObject(operaServiceimpl.queryByDate(parmMap)).toString();
    }


}
