package cn.com.caogen.filter;

import cn.com.caogen.entity.User;
import cn.com.caogen.util.DateUtil;
import cn.com.caogen.util.JedisUtil;
import cn.com.caogen.util.SerializeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.data.redis.connection.jedis.JedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.Map;
import redis.clients.jedis.Jedis;

/**
 * author:huyanqing
 * Date:2018/5/22
 */
public class SystemListener implements HttpSessionListener {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public void sessionCreated(HttpSessionEvent se) {


        System.out.println(se.getSession().getId()+"createsession=============================="+DateUtil.getTime());
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        JedisUtil.getJedis().del(se.getSession().getId().getBytes());
        System.out.println(se.getSession().getMaxInactiveInterval()+"====="+se.getSession().getId()+"destorysession=============================="+DateUtil.getTime());
    }
}
