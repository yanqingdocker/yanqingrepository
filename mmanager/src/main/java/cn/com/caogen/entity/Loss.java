package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * author:huyanqing
 * Date:2018/7/24
 */
@Setter
@Getter
public class Loss {
    private int id;
    /**
     * 损耗项目名称
     */
    private String projectname;
    /**
     * 损耗金额类型
     */
    private String moneytype;
    /***
     * 损耗金额
     */
    private double num;
    /**
     * 操作人
     */
    private String operauser;
    /**
     * 损耗时间
     */
    private String creattime;
    /**
     * 备注
     */
    private String remark;
}
