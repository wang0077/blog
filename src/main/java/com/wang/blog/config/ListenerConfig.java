package com.wang.blog.config;

import com.wang.blog.component.RedisListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author wangsiyuan
 */
@Configuration
public class ListenerConfig {

    RedisListener redisListener;

    @Autowired
    public void setRedisListener(RedisListener redisListener) {
        this.redisListener = redisListener;
    }

    @Bean
    public ServletListenerRegistrationBean<RedisListener> registrationBean(){
        ServletListenerRegistrationBean<RedisListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(redisListener);
        registrationBean.setOrder(1);
        return registrationBean;
    }
}
