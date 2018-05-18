package cn.com.caogen.service;

import cn.com.caogen.entity.Task;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/18
 */
@Service
public interface ITaskService {
    void addTask(Task task);
    void updateTask(Map<String,Object> parmMap);
    List<Task> queryByState(String state);
    List<Task> queryAll();
}
