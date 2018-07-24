package cn.com.caogen.service;

import cn.com.caogen.entity.Profits;

import java.util.List;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
public interface ProfitsService {
    /**
     * 增加分配记录
     * @param profits
     */
    void add(Profits profits);

    /**
     * 查询分配记录
     * @return
     */
    List<Profits> queryAll();
}
