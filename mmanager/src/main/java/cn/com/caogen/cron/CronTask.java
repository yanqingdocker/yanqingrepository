package cn.com.caogen.cron;


import cn.com.caogen.controller.UserController;
import cn.com.caogen.externIsystem.service.RateService;
import cn.com.caogen.util.ConstantUtil;
import cn.com.caogen.util.DataMonitor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * author:huyanqing
 * Date:2018/4/23
 */
@Component
public class CronTask {

    private static Logger logger = LoggerFactory.getLogger(CronTask.class);

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * //0 0/2 * * * ?  每隔2分钟打印一次
     *  每天0晨12点调用一次汇率接口，并刷新redis //1 0 0 * * ?
     */
    @Scheduled(cron = "1 0 0 * * ? ")
    public void setRate(){
            String result = RateService.getRequest2();
             result=DataMonitor.reSet(result);
            stringRedisTemplate.opsForValue().set(ConstantUtil.ONE,stringRedisTemplate.opsForValue().get(ConstantUtil.TWO));
            stringRedisTemplate.opsForValue().set(ConstantUtil.TWO,stringRedisTemplate.opsForValue().get(ConstantUtil.THREE));
            stringRedisTemplate.opsForValue().set(ConstantUtil.THREE,stringRedisTemplate.opsForValue().get(ConstantUtil.FOURE));
            stringRedisTemplate.opsForValue().set(ConstantUtil.FOURE,stringRedisTemplate.opsForValue().get(ConstantUtil.FIVE));
            stringRedisTemplate.opsForValue().set(ConstantUtil.FIVE,stringRedisTemplate.opsForValue().get(ConstantUtil.SIX));
            stringRedisTemplate.opsForValue().set(ConstantUtil.SIX,stringRedisTemplate.opsForValue().get(ConstantUtil.SENVEN));
            stringRedisTemplate.opsForValue().set(ConstantUtil.SENVEN,result);

        logger.info("now time:" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
