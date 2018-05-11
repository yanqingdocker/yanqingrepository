package cn.com.caogen.controller;

import cn.com.caogen.entity.Authoirty;
import cn.com.caogen.service.IAuthoirtyService;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.ResponseMessage;
import cn.com.caogen.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * author:huyanqing
 * Date:2018/5/11
 */
@RestController
@RequestMapping("/authoirty")
public class AuthoirtyController {
    private static Logger logger = LoggerFactory.getLogger(AuthoirtyController.class);
    @Autowired
    private IAuthoirtyService authoirtyService;

    /**
     * 添加接口
     * @param authoirtyname
     * @param url
     * @return
     */
    @RequestMapping(path = "add",method =RequestMethod.POST )
    public String add(@RequestParam("authoirtyname") String authoirtyname,@RequestParam("url") String url){
        if(!StringUtil.checkStrs(authoirtyname,url)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Authoirty authoirty=new Authoirty();
        authoirty.setAuthoirtyname(authoirtyname);
        authoirty.setUrl(url);
        authoirtyService.add(authoirty);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 修改接口
     * @param id
     * @param authoirtyname
     * @param url
     * @return
     */
    @RequestMapping(path = "update",method =RequestMethod.POST )
    public String update(@RequestParam("id") String id,@RequestParam("authoirtyname") String authoirtyname,@RequestParam("url") String url){
        if(!StringUtil.checkStrs(id,authoirtyname,url)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Authoirty authoirty=authoirtyService.queryById(Integer.parseInt(id));
        if(authoirty==null){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();
        }
        authoirty.setAuthoirtyname(authoirtyname);
        authoirty.setUrl(url);
        authoirtyService.update(authoirty);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();

    }

    /**
     * 删除接口
     * @param ids
     * @return
     */
    @RequestMapping(path = "/delete",method =RequestMethod.GET )
    public String delete(@RequestParam("ids") String ids){
        if (!StringUtil.checkStrs(ids)) {
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        authoirtyService.delete(ids);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();


    }

    /**
     * 查询所有接口
     * @return
     */
    @RequestMapping(path = "queryAll",method =RequestMethod.GET )
    public String queryAll(){
        return JSONArray.fromObject(authoirtyService.queryAll()).toString();
    }

}
