package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * author:huyanqing
 * Date:2018/4/12
 */
@Setter
@Getter
public class Operation {
    //id
    private int id;
    //账户id
    private String countid;
    //操作金额
    private String num;
    //操作类型
    private String operaType;
    //操作时间
    private String operaTime;
    //操作人
    private String operaUser;

    //收入/支出
    private int oi;


}
