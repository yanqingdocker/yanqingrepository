package cn.com.caogen.controller;

import cn.com.caogen.entity.BankCard;
import cn.com.caogen.externIsystem.service.BackCardService;
import cn.com.caogen.service.IBankCardService;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.ResponseMessage;
import cn.com.caogen.util.StringUtil;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/4/23
 */
@RestController
@RequestMapping("/bank")
public class BankController {

    private static Logger logger = LoggerFactory.getLogger(BankController.class);

    @Autowired
    private IBankCardService bankCardService;

    /**
     * 获取银行卡类型的logoUrl
     *
     * @param bankCardId
     * @return
     */
    @RequestMapping(path = "/getType", method = RequestMethod.GET)
    public String getBankType(@RequestParam("bankcardid") String bankCardId) {
        logger.info("getBankType start: bankCardId="+bankCardId);
        if (StringUtil.checkStrs(bankCardId)) {
            String result = BackCardService.getBackType(bankCardId);
            if (!StringUtils.isEmpty(result)) {
                return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS, result)).toString();
            }
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, ConstantUtil.FAILSYSTEM)).toString();
        }else {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }


    }

    /**
     * 校验银行卡四元素
     *
     * @param datas
     * @return
     */
    @RequestMapping(path = "/bindBankCard", method = RequestMethod.POST)
    public String bindBankCard(@RequestParam("datas") String datas, HttpServletRequest request) {
        logger.info("bindBankCard start: datas="+datas);
        if (StringUtil.checkStrs(datas)) {
            JSONObject jsonObject = JSONObject.fromObject(datas);
            String username = jsonObject.getString("username");
            String idcard = jsonObject.getString("idcard");
            String bankcard = jsonObject.getString("bankcard");
            String phone = jsonObject.getString("phone");
            String address = jsonObject.getString("address");
            String userid =String.valueOf(request.getSession().getAttribute("userid"));
            String bankType = jsonObject.getString("banktype");

            if (StringUtil.checkStrs(username, idcard, bankcard, phone)) {
                boolean falg = BackCardService.checkBackCard(username, idcard, bankcard, phone);
                if (falg) {
                    Map<String, String> parmMap = new HashMap<String, String>();
                    parmMap.put("username", username);
                    parmMap.put("idcard", idcard);
                    parmMap.put("bankcard", bankcard);
                    parmMap.put("phone", phone);
                    parmMap.put("address", address);
                    parmMap.put("userid", userid);
                    parmMap.put("banktype", bankType);
                    bankCardService.add(parmMap);
                    return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
                } else {
                    return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, "验证不通过")).toString();
                }
            }
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL, "绑定失败")).toString();
        }else {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }

    }


    /**
     * 解绑银行卡
     */
    @RequestMapping(path = "/unbind", method = RequestMethod.POST)
    public String unbind(@RequestParam("id") String id) {
        logger.info("unbind start: id="+id);
        if (StringUtil.checkStrs(id)) {
            bankCardService.delete(Integer.parseInt(id));
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS, "解绑成功")).toString();
        }else {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }

    }

    /**
     * 查询当前用户下的银行卡
     */
    @RequestMapping(path = "/query", method = RequestMethod.POST)
    public String query(HttpServletRequest request) {
        logger.info("query start");
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("userid", (int) request.getSession().getAttribute("userid"));
        List<BankCard> bankCardList = bankCardService.queryCondition(parmMap);
        return JSONArray.fromObject(bankCardList).toString();
    }
    /**
     * 查询当前用户下的银行卡
     */
    @RequestMapping(path = "/queryAll", method = RequestMethod.GET)
    public String queryAll(HttpServletRequest request) {
        logger.info("query start");
        Map<String, Object> parmMap = new HashMap<String, Object>();
        List<BankCard> bankCardList = bankCardService.queryCondition(parmMap);
        return JSONArray.fromObject(bankCardList).toString();
    }
}
