package cn.com.caogen.controller;

import cn.com.caogen.entity.Role;
import cn.com.caogen.service.IRoleService;
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
@RequestMapping("/role")
public class RoleController {
    private static Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private IRoleService roleServiceimpl;

    /**
     * 增加角色
     * @param rolename
     * @return
     */
    @RequestMapping(path = "addRole",method = RequestMethod.POST)
    public String addRole(@RequestParam("rolename") String rolename){
        if(!StringUtil.checkStrs(rolename)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        roleServiceimpl.add(rolename);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 删除角色
     * @param id
     * @return
     */
    @RequestMapping(path = "deleteRole",method = RequestMethod.POST)
    public String deleteRole(@RequestParam("id") int id){
        if(!StringUtil.checkStrs(String.valueOf(id))){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        roleServiceimpl.delete(String.valueOf(id));
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    @RequestMapping(path = "queryRole",method = RequestMethod.GET)
    public String queryRole(@RequestParam("id") String id){
        if(!StringUtil.checkStrs(id)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Role role=roleServiceimpl.queryById(Integer.parseInt(id));
        return JSONObject.fromObject(role).toString();
    }

    /**
     * 查询所有角色
     * @return
     */
    @RequestMapping(path = "queryAll",method = RequestMethod.GET)
    public String queryAll(){
        List<Role> roleList=roleServiceimpl.queryAll();
        return JSONArray.fromObject(roleList).toString();
    }

    /**
     * 修改角色
     * @param id
     * @param rolename
     * @return
     */
    @RequestMapping(path = "updateRole",method = RequestMethod.POST)
    public String updateRole(@RequestParam("id") String id,@RequestParam("rolename") String rolename){
        if(!StringUtil.checkStrs(id,rolename)){
            return JSONObject.fromObject(new ResponseMessage(ConstantUtil.FAIL,ConstantUtil.ERROR_ARGS)).toString();
        }
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("id",Integer.parseInt(id));
        parmMap.put("rolename",rolename);
        roleServiceimpl.update(parmMap);
        return JSONObject.fromObject(new ResponseMessage(ConstantUtil.SUCCESS)).toString();
    }


}
