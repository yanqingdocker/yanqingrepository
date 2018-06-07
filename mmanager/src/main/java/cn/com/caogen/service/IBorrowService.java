package cn.com.caogen.service;

import cn.com.caogen.entity.Borrow;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/7
 */
public interface IBorrowService {
    void add(Borrow borrow);
    List<Borrow> queryAll();
    void update(int id);
}
