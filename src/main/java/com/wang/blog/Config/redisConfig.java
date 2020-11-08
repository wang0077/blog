package com.wang.blog.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.annotations.Beta;
import com.google.common.base.Charsets;
import com.google.common.hash.Funnel;
import com.wang.blog.component.RedisListener;
import com.wang.blog.component.bloomfilter.BloomFilterHelper;
import com.wang.blog.component.bloomfilter.BloomFilterServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.net.UnknownHostException;

/**
 * Redis序列化配置
 * @author wangsiyuan
 * @date 2020/10/14
 */

@Configuration
public class RedisConfig {

    private static final Logger logger =  LoggerFactory.getLogger(RedisListener.class);

    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        Jackson2JsonRedisSerializer<Object> serializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        redisTemplate.setDefaultSerializer(serializer);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        return redisTemplate;
    }

    @Bean
    @SuppressWarnings("all")
    public BloomFilterHelper<String> bloomFilterHelper(){
        logger.info("布隆过滤器初始化完成");
        return new BloomFilterHelper<>((from, into) -> into.putString(from, Charsets.UTF_8)
                .putString(from, Charsets.UTF_8), 10000, 0.0001);
    }

    @Bean
    public BloomFilterServer bloomFilterServer(){
        return new BloomFilterServer();
    }
}
