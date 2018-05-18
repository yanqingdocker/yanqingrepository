package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/7
 */
@Setter
@Getter
public class Role {
    private int id;
    private String rolename;
    private String createtime;
    private Map<Integer,String> authMap=new HashMap<Integer,String>();
}
