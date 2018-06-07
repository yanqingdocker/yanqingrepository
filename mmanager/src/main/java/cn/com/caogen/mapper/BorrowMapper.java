package cn.com.caogen.mapper;

import cn.com.caogen.entity.Borrow;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/6/7
 */
@Repository
public interface BorrowMapper {
    void add(Borrow borrow);
    List<Borrow> queryAll();

    void update(int id);
}
