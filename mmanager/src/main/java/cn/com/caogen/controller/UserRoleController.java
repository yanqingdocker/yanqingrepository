package cn.com.caogen.controller;

import cn.com.caogen.entity.RoleAuth;
import cn.com.caogen.entity.UserRole;
import cn.com.caogen.mapper.UserRoleMapper;
import cn.com.caogen.service.IRoleAuthService;
import cn.com.caogen.service.IUserRoleService;
import cn.com.caogen.service.UserRoleServiceImpl;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/9
 */
@RestController
@RequestMapping("/userrole")
public class UserRoleController {
    private static Logger logger = LoggerFactory.getLogger(UserRoleController.class);
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private UserRoleMapper userRoleMapper;



    @RequestMapping(path = "add",method = RequestMethod.POST)
    public String add(@RequestParam("roleid") int roleid, @RequestParam("userid") int userid){
        UserRole userRole=new UserRole();
       userRole.setRoleid(roleid);
       userRole.setUserid(userid);
       userRoleService.add(userRole);
        return "successs";
    }


    @RequestMapping(path = "queryByUserid",method = RequestMethod.POST)
    public String queryByRoleid(@RequestParam("userid") int userid){
        List<UserRole> userRoles= userRoleService.queryByUserId(userid);
        return JSONArray.fromObject(userRoles).toString();
    }
    @RequestMapping(path="/batchAdd",method = RequestMethod.POST)
    public String batchAdd(){
        List<UserRole> roles=new ArrayList<UserRole>();
        UserRole userRole=new UserRole();
        userRole.setUserid(10);
        userRole.setRoleid(10);
        UserRole userRole1=new UserRole();
        userRole1.setUserid(11);
        userRole1.setRoleid(11);
        roles.add(userRole);
        roles.add(userRole1);
        userRoleMapper.batchAdd(roles);
        return "suuceee";
    }




}
