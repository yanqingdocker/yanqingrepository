package cn.com.caogen.controller;

import cn.com.caogen.externIsystem.service.RateService;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DataMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * author:huyanqing
 * Date:2018/4/23
 */

@Component
@Order(value = 1)
public class Starup implements CommandLineRunner {

    private static Logger logger = LoggerFactory.getLogger(Starup.class);
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 系统启动时首先调用汇率接口存入redis
     *
     * @param args
     * @throws Exception
     */
    @Override
    public void run(String... args) throws Exception {
            String result = RateService.getRequest2();
            result=DataMonitor.reSet(result);

            stringRedisTemplate.opsForValue().set(ConstantUtil.ONE, result);


            stringRedisTemplate.opsForValue().set(ConstantUtil.TWO, result);


            stringRedisTemplate.opsForValue().set(ConstantUtil.THREE, result);


            stringRedisTemplate.opsForValue().set(ConstantUtil.FOURE, result);


            stringRedisTemplate.opsForValue().set(ConstantUtil.FIVE, result);


            stringRedisTemplate.opsForValue().set(ConstantUtil.SIX, result);

            stringRedisTemplate.opsForValue().set(ConstantUtil.SENVEN, result);

    }
}
