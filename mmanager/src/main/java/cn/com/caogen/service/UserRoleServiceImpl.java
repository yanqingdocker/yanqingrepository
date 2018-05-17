package cn.com.caogen.service;

import cn.com.caogen.entity.UserRole;
import cn.com.caogen.mapper.UserRoleMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/5/17
 */
@Service
public class UserRoleServiceImpl implements IUserRoleService {
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public void add(UserRole userRole) {
            userRoleMapper.add(userRole);
    }

    @Override
    public List<UserRole> queryByUserId(int userid) {
        return userRoleMapper.queryByUserId(userid);
    }
}
