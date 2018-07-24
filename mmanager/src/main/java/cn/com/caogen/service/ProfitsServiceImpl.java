package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Profits;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.mapper.ProfitsMapper;
import cn.com.caogen.util.ConstantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Service
public class ProfitsServiceImpl implements ProfitsService {
    @Autowired
    private ProfitsMapper profitsMapper;
    @Autowired
    private CashPoolMapper cashPoolMapper;
    @Override
    public void add(Profits profits) {
        List<CashPool> cashPools=cashPoolMapper.queryAll();
        CashPool temp=null;
        for(CashPool cashPool:cashPools){
            if(cashPool.getServicebranch().equals(ConstantUtil.SERVICE_BRANCH)&&cashPool.getCounttype().equals(profits.getMoneytype())){
                temp=cashPool;
                break;
            }
        }
        if(temp!=null){
            temp.setBlance(temp.getBlance()-profits.getNum());

        }
        //更新资金库
        cashPoolMapper.update(temp);
        profitsMapper.add(profits);
    }

    @Override
    public List<Profits> queryAll() {
        return profitsMapper.queryAll();
    }
}
