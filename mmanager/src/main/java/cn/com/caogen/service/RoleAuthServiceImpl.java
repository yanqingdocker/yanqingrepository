package cn.com.caogen.service;

import cn.com.caogen.entity.RoleAuth;
import cn.com.caogen.mapper.RoleAuthMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public class RoleAuthServiceImpl implements IRoleAuthService {
    @Autowired
    private RoleAuthMapper roleAuthMapper;
    @Override
    public void add(RoleAuth roleAuth) {
        roleAuthMapper.add(roleAuth);
    }

    @Override
    public List<RoleAuth> queryByRoleId(int roleid) {
        return roleAuthMapper.queryByRoleId(roleid);
    }
}
