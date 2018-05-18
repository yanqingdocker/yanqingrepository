package cn.com.caogen.controller;

import cn.com.caogen.entity.Task;
import cn.com.caogen.service.ITaskService;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DateUtil;
import net.sf.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/18
 */
@RestController
@RequestMapping("/task")
public class TaskController {
    private static Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private ITaskService taskService;
    @RequestMapping("add")
    public String add(){
        Task task=new Task();
        task.setCreatetime(DateUtil.getTime());
        task.setState("处理中");
        task.setTaskname("转账申请");
        task.setOperauser("admin");
        task.setTaskcontent("转账100");
        taskService.addTask(task);
        return "success";
    }

    /**
     * 查询未处理的任务
     * @return
     */
    @RequestMapping("queryUndo")
    public String queryUndo(){
        logger.info("queryUndo start:");
        return JSONArray.fromObject(taskService.queryByState(ConstantUtil.TASK_UNDO)).toString();
    }

    /**
     * 查询已处理的任务
     * @return
     */
    @RequestMapping("queryDone")
    public String queryDone(){
        logger.info("queryDone start:");
        return JSONArray.fromObject(taskService.queryByState(ConstantUtil.TASK_DONE)).toString();
    }
    @RequestMapping("dotask")
    public String doTask(@RequestParam("id") int id){
        logger.info("dotask start:");
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("id",id);
        parmMap.put("state",ConstantUtil.TASK_DONE);
        taskService.updateTask(parmMap);
        return "suceess";
    }

    @RequestMapping("queryAll")
    public String queryAll(){
        logger.info("queryAll start:");
        return JSONArray.fromObject(taskService.queryAll()).toString();
    }
}
