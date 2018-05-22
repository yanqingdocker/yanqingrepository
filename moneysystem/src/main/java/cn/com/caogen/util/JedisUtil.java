package cn.com.caogen.util;

import cn.com.caogen.entity.User;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

/**
 * author:huyanqing
 * Date:2018/5/22
 */
public class JedisUtil {
    public static Jedis jedis;
    public static Jedis getJedis(){
        if(jedis!=null){
            return jedis;
        }
        jedis = new Jedis("127.0.0.1",6379);
        return jedis;
    }
    public static User getUser(HttpServletRequest request){
        Map<String,Object> map=(Map)SerializeUtil.unserialize(JedisUtil.getJedis().get(ConstantUtil.SESSIONCOLLCTION.getBytes()));
        if(map==null||map.get(request.getSession().getId())==null){
            return null;
        }
        User currentUser=(User)SerializeUtil.unserialize((byte[])map.get(request.getSession().getId()));
        return currentUser;
    }
    public static Map<String,Object> getSessionMap(){
        Map<String,Object> sessionMap=(Map)SerializeUtil.unserialize(JedisUtil.getJedis().get(ConstantUtil.SESSIONCOLLCTION.getBytes()));
        if(sessionMap==null){
            return null;
        }
        return sessionMap;
    }
}
