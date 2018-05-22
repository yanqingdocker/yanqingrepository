package cn.com.caogen.util;

import redis.clients.jedis.Jedis;

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
}
