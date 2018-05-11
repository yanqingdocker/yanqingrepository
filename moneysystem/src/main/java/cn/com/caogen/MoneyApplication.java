package cn.com.caogen;


import cn.com.caogen.filter.SystemFilter;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.FilterRegistration;

/**
 * author:huyanqing
 * Date:2018/4/11
 */

@SuppressWarnings("ALL")
@ComponentScan(basePackages ={"cn.com.caogen.cron","cn.com.caogen.service","cn.com.caogen.controller","cn.com.caogen.respontory"})
@MapperScan("cn.com.caogen.mapper")
@SpringBootApplication
@EnableScheduling
public class MoneyApplication {
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    public static void main(String[] args) {
        SpringApplication.run(MoneyApplication.class, args);

    }
 /*   @Bean
    public FilterRegistrationBean indexFilter(){

        FilterRegistrationBean registration = new FilterRegistrationBean(new SystemFilter());
        return registration;
    }*/


}
