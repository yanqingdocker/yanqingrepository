package cn.com.caogen.service;

import cn.com.caogen.entity.CashPool;
import cn.com.caogen.entity.Operation;
import cn.com.caogen.mapper.CashPoolMapper;
import cn.com.caogen.mapper.OperaMapper;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DataMonitor;
import cn.com.caogen.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import java.util.List;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public class CashPoolServiceImpl implements ICashPoolService {
    @Autowired
    private CashPoolMapper cashPoolMapper;
    @Autowired
    private DataSourceTransactionManager transactionManager;
    @Autowired
    private OperaMapper operaMapper;
    @Override
    public List<CashPool> queryAll() {
        return cashPoolMapper.queryAll();
    }

    @Override
    @Transactional
    public void exchange(Map<String, Object> parmMap) {

        DefaultTransactionDefinition def=new DefaultTransactionDefinition();
        def.setName("exchange");
        def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
        TransactionStatus status=transactionManager.getTransaction(def);
        try{
            //更新转出现金库数据
            CashPool srccount=(CashPool)parmMap.get("srccount");
            srccount.setBlance(srccount.getBlance()-(Double)parmMap.get("srcnum"));
            srccount.setLasttime(DateUtil.getTime());
            cashPoolMapper.update(srccount);
           //更新转入现金库数据
            CashPool destcount=(CashPool)parmMap.get("destcount");
            destcount.setBlance(destcount.getBlance()+(Double)parmMap.get("destnum"));
            destcount.setLasttime(DateUtil.getTime());
            cashPoolMapper.update(destcount);
            //增加兑换转出记录
            Operation srcoperation=new Operation();
            srcoperation.setOperaIp((String)parmMap.get("ip"));
            srcoperation.setOi(ConstantUtil.MONEY_OUT);
            srcoperation.setOperaUser((String)parmMap.get("operauser"));
            srcoperation.setOperaType(ConstantUtil.MONEY_EXCHANGE);
            srcoperation.setNum(-(Double)parmMap.get("srcnum"));
            srcoperation.setCountType(srccount.getCounttype());
            srcoperation.setOperaTime(DateUtil.getTime());
            operaMapper.add(srcoperation);
            //增加兑换转入的记录
            Operation destoperation=new Operation();
            destoperation.setOperaIp((String)parmMap.get("ip"));
            destoperation.setOi(ConstantUtil.MONEY_IN);
            destoperation.setOperaUser((String)parmMap.get("operauser"));
            destoperation.setOperaType(ConstantUtil.MONEY_EXCHANGE);
            destoperation.setNum((Double)parmMap.get("destnum"));
            destoperation.setCountType(destcount.getCounttype());
            destoperation.setOperaTime(DateUtil.getTime());

            operaMapper.add(destoperation);


       }catch (Exception e){
            //有一个不成功能则回滚事务
            transactionManager.rollback(status);

        }

    }

    @Override
    public CashPool queryByType(String type) {
        return cashPoolMapper.queryByType(type);
    }
}
