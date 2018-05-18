package cn.com.caogen.controller;

import cn.com.caogen.entity.Role;
import cn.com.caogen.entity.RoleAuth;
import cn.com.caogen.service.IRoleAuthService;
import cn.com.caogen.service.IRoleService;
import cn.com.caogen.service.RoleAuthServiceImpl;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/9
 */
@RestController
@RequestMapping("/roleauth")
public class RoleAuthController {
    private static Logger logger = LoggerFactory.getLogger(RoleAuthController.class);
    @Autowired
    private RoleAuthServiceImpl roleAuthService;
    @RequestMapping(path = "add",method = RequestMethod.POST)
    public String add(@RequestParam("roleid") int roleid,@RequestParam("authid") int authid){
        RoleAuth roleAuth=new RoleAuth();
        roleAuth.setRoleid(roleid);
        roleAuth.setAuthid(authid);
        roleAuthService.add(roleAuth);
        return "successs";
    }

    @RequestMapping(path = "queryByRoleid",method = RequestMethod.POST)
    public String queryByRoleid(@RequestParam("roleid") int roleid){
        List<RoleAuth> roleAuths= roleAuthService.queryByRoleId(roleid);
        return JSONArray.fromObject(roleAuths).toString();
    }

    @RequestMapping(path="/batchupdate",method = RequestMethod.POST)
    public String batchupdate(@RequestParam("roleid") int roleid,@RequestParam("authids") String authids){
        roleAuthService.batchAdd(roleid,authids);
        return "suuceee";
    }



}
