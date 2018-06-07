package cn.com.caogen.service;

import cn.com.caogen.entity.Borrow;
import cn.com.caogen.mapper.BorrowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/7
 */
@Service
public class BorrowServiceImpl implements IBorrowService {
    @Autowired
    private BorrowMapper borrowMapper;
    @Override
    public void add(Borrow borrow) {
        borrowMapper.add(borrow);
    }

    @Override
    public List<Borrow> queryAll() {
        return borrowMapper.queryAll();
    }

    @Override
    public void update(int id) {
        borrowMapper.update(id);

    }
}
