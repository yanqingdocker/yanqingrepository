package cn.com.caogen.service;

import cn.com.caogen.entity.ServiceBranch;
import cn.com.caogen.mapper.ServiceBranchMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/4
 */
@Service
public class ServiceBranchImpl implements IServiceBranch {
    @Autowired
    private ServiceBranchMapper serviceBranchMapper;
    @Override
    public void add(ServiceBranch serviceBranch) {
        serviceBranchMapper.add(serviceBranch);
    }

    @Override
    public void delete(int id) {
        serviceBranchMapper.delete(id);
    }

    @Override
    public void update(ServiceBranch serviceBranch) {
        serviceBranchMapper.update(serviceBranch);
    }

    @Override
    public List<ServiceBranch> queryAll() {
        return serviceBranchMapper.queryAll();
    }

    @Override
    public ServiceBranch queryById(int id) {
        return serviceBranchMapper.queryById(id);
    }
}
