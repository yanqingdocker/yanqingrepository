package cn.com.caogen.controller;

import cn.com.caogen.entity.Muser;
import cn.com.caogen.entity.Role;
import cn.com.caogen.entity.RoleAuth;
import cn.com.caogen.entity.UserRole;
import cn.com.caogen.service.*;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * author:huyanqing
 * Date:2018/5/3
 */
@RestController
@RequestMapping("/muser")
public class MuserController {
    private static Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private IUserService userServiceImpl;
    @Autowired
    private UserRoleServiceImpl userRoleService;
    @Autowired
    private RoleAuthServiceImpl roleAuthService;
    @Autowired
    private AuthoirtyServiceImpl authoirtyService;
    @Autowired
    private RoleServiceImpl roleService;
    private static Map<String,HttpSession> sessionMap =new HashMap<String,HttpSession>();

    private static Map<String,HttpServletResponse> respMap =new HashMap<String,HttpServletResponse>();

    private static Map<Integer,String> userMap =new HashMap<Integer,String>();


    /**
     * 删除成员信息
     * @param ids
     * @return
     */
    @RequestMapping(path = "/batchdelete", method = RequestMethod.GET)
    public String batchdelete(@RequestParam("ids") String ids) {
        logger.info("batchdelete start: ids="+ids);
        if (StringUtil.checkStrs(ids)) {
            userServiceImpl.deleteUser(ids);
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
        }else {
            return net.sf.json.JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
    }

    /**
     * 查看成员信息
     * @return
     */
    @RequestMapping(path = "/queryAll", method = RequestMethod.GET)
    public String queryAll() {
        logger.info("queryMuser startst: ");
        List<Muser> musers= userServiceImpl.queryMusers();
        for (Muser muser:musers){
            List<UserRole> userRoles=userRoleService.queryByUserId(muser.getId());
            for (UserRole userRole:userRoles){
                Role role=roleService.queryById(userRole.getRoleid());
                muser.getIdlist().add(role.getId());
                muser.getNamelist().add(role.getRolename());
            }
        }
        if(musers!=null){
            return JSONArray.fromObject(musers).toString();
        }
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();

    }

    /**
     * 重置密码
     * @param id
     * @param password
     * @return
     */
    @RequestMapping(path="/restPwd",method = RequestMethod.POST)
    public String update(@RequestParam("id") String id,@RequestParam("password") String password){
        userServiceImpl.updateMuser(id,password);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }



    /**
     * 添加成员信息
     * @return
     */
    @RequestMapping(path ="/add", method = RequestMethod.POST)
    public String add(@RequestParam("username") String username,@RequestParam("password") String password,@RequestParam("roleids") String roleids) {
        logger.info("add start: ");
        if(!StringUtil.checkStrs(username,password)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Muser muser=new Muser();
        muser.setUsername(username);
        muser.setPassword(password);
        userServiceImpl.addMuser(muser);
        List<Muser> musers=userServiceImpl.queryMusers();
        int userid=0;
        for (Muser temp:musers){
            if(temp.getUsername().equals(muser.getUsername())&&temp.getPassword().equals(muser.getPassword())){
                userid=temp.getId();
                break;
            }
        }
        userRoleService.batchAdd(userid,roleids);
        return net.sf.json.JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 登录系统
     * @return
     */
    @RequestMapping(path = "/login", method = RequestMethod.POST)
    public String login(@RequestParam("username") String username,@RequestParam("password") String password,HttpServletRequest request,HttpServletResponse response) {
        logger.info("login start: ");
        int i=6;
        boolean flag=StringUtil.checkStrs(username,password);
        if(!flag){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        List<Muser> musers= userServiceImpl.queryMusers();
        if(!musers.isEmpty()){
            for(Muser muser:musers){
                if(username.equals(muser.getUsername())&&password.equals(muser.getPassword())){
                    request.getSession().setAttribute("userid",muser.getId());
                    request.getSession().setAttribute("username",muser.getUsername());
                    request.getSession().setMaxInactiveInterval(180);
                    getAuth(muser);
                    request.getSession().setAttribute("currentUser",muser);
                    return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
                }
            }
        }

        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL)).toString();

    }

    /**
     * 退出登录
     * @param request
     * @return
     */
    @RequestMapping(path = "/logout",method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        logger.info("logout start");
        sessionMap.remove(request.getSession().getId());
        userMap.remove(request.getSession().getAttribute("userid"));
        request.getSession().invalidate();


        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    private void getAuth(Muser muser){
       List<UserRole> userRoles=userRoleService.queryByUserId(muser.getId());
       for(UserRole userRole:userRoles){
           int roleId=userRole.getRoleid();
           List<RoleAuth> roleAuths=roleAuthService.queryByRoleId(roleId);
           for (RoleAuth roleAuth:roleAuths){
               muser.getAuths().add(authoirtyService.queryById(roleAuth.getAuthid()).getUrl());
           }
       }
    }


}
