package cn.com.caogen.service;

import cn.com.caogen.entity.Operation;
import cn.com.caogen.mapper.OperaMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/10
 */
@Service
public class OperaServiceImpl implements IOperaService {
    @Autowired
    private OperaMapper operaMapper;
    @Override
    public void add(Operation operation) {
            operaMapper.add(operation);
    }

    @Override
    public List<Operation> queryAll(String servicebranch) {
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryCondition(parmMap);
    }

    @Override
    public List<Operation> queryAll(Map<String, Object> parmMap) {

        return operaMapper.queryCondition(parmMap);
    }

    @Override
    public List<Operation> queryByDate(Map<String, Object> parmMap) {
        return operaMapper.queryByDate(parmMap);
    }

    @Override
    public List<Map<String, Object>> queryoperatype(int date,String servicebranch) {
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("date",date);
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryoperatype(parmMap);
    }

    @Override
    public List<Map<String, Object>> queryoperacount(int date,String servicebranch) {
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("date",date);
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryoperacount(parmMap);
    }
}
