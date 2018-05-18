package cn.com.caogen.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

/**
 * author:huyanqing
 * Date:2018/5/3
 */
@Setter
@Getter
public class Muser {
    private int id;
    private String username;
    private String password;
    private Map<Integer,Object> roleMap=new HashMap<Integer,Object>();
}
