package cn.com.caogen.service;

import cn.com.caogen.entity.Operation;
import cn.com.caogen.mapper.OperaMapper;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.StringUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
        if(ConstantUtil.SERVICE_BRANCH.equals(servicebranch)){
            return operaMapper.queryCondition(parmMap);
        }
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryCondition(parmMap);
    }

    @Override
    public List<Operation> queryAll(Map<String, Object> parmMap) {

        return operaMapper.queryCondition(parmMap);
    }

    @Override
    public List<Operation> queryByDate(Map<String, Object> parmMap) {
        if(ConstantUtil.SERVICE_BRANCH.equals(parmMap.get("servicebranch"))){
            parmMap.remove("servicebranch");
        }
        return operaMapper.queryByDate(parmMap);
    }

    @Override
    public List<Map<String, Object>> queryoperatype(int date,String servicebranch) {
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("date",String.valueOf(date));
        if(ConstantUtil.SERVICE_BRANCH.equals(servicebranch)){
            return operaMapper.queryoperatype(parmMap);
        }
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryoperatype(parmMap);
    }

    @Override
    public List<Map<String, Object>> queryoperacount(int date,String servicebranch) {
        Map<String,Object> parmMap=new HashMap<String,Object>();
        parmMap.put("date",String.valueOf(date));

        if(ConstantUtil.SERVICE_BRANCH.equals(servicebranch)){
            return operaMapper.queryoperacount(parmMap);
        }
        parmMap.put("servicebranch",servicebranch);
        return operaMapper.queryoperacount(parmMap);
    }

    @Override
    public String queryScope(Map<String, String> parmMap) {
        List<Operation> operationList=operaMapper.queryScope(parmMap);
        List<Operation> inUSD=operationList.stream().filter((e)->ConstantUtil.USD_LIB.equals(e.getCountid())&&e.getNum()>0).collect(Collectors.toList());
        List<Operation> outUSD=operationList.stream().filter((e)->ConstantUtil.USD_LIB.equals(e.getCountid())&&e.getNum()<0).collect(Collectors.toList());
        double inNum=0;
        for(Operation operation:inUSD){
           inNum+=operation.getNum();
        }

        double outNum=0;
        for(Operation operation:outUSD){
            outNum+=operation.getNum();
        }
        StringBuffer sb=new StringBuffer();
        sb.append("{'inNum':'").append(inNum).append("','outNum':'").append(outNum).append("'}");


        return JSONObject.fromObject(sb.toString()).toString();
    }
}
