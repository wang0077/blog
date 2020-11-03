package com.wang.blog.cache.redis.impl;


import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.wang.blog.bean.Type;
import com.wang.blog.cache.redis.ITypeByRedis;
import com.wang.blog.util.RedisUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wangsiyuan
 */
@Service
public class TypeByRedis implements ITypeByRedis {

    RedisTemplate<String,Object> redisTemplate;

    RedisUtil redisUtil;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    @Override
    public void countTypeByBlog() {
    }

    @Override
    public void updateType(Long id,Type type) {
        deleteType(id);
        setType(type);
    }

    @Override
    public void deleteType(Long id) {
        redisTemplate.opsForZSet().removeRangeByScore("Type",id,id);
    }

    @Override
    public Long countType() {
        return redisTemplate.opsForZSet().zCard("Type");
    }

    @Override
    public List<Type> getTypeByPage(int start, int end) {
        Set<Object> set = redisTemplate.opsForZSet().range("Type", start, end);
        List<Type> types = new LinkedList<>();
        if(set != null){
            for (Object t : set){
                Type type = BeanUtil.mapToBean((Map<?, ?>) t, Type.class, false, CopyOptions.create());
                types.add(type);
            }
        }
        return types;
    }

    @Override
    public Type getTypeById(Long typeId) {
        Set<Object> set = redisTemplate.opsForZSet().rangeByScore("Type", typeId, typeId);
        Type type = null;
        if (set != null) {
            for (Object t : set){
                @SuppressWarnings(value = "all")
                Map<Object,Object> checkType = (Map<Object, Object>) t;
                type = BeanUtil.mapToBean(checkType, Type.class, false, CopyOptions.create());
            }
        }
        return type;
    }

    @Override
    public Type getTypeByName(@NotNull String name) {
        Set<Object> set = redisTemplate.opsForZSet().range("Type", 0, -1);
        Type type = null;
        if (set != null) {
            for (Object t : set){
                @SuppressWarnings(value = "all")
                Map<Object,Object> checkType = (Map<Object, Object>) t;
                if(name.equals(checkType.get("name"))){
                    type = BeanUtil.mapToBean(checkType, Type.class, false, CopyOptions.create());
                }
            }
        }
        return type;
    }

    @Override
    public List<Type> listType() {
        Set<Object> set = redisTemplate.opsForZSet().range("Type", 0, -1);
        List<Type> types = null;
        if(set != null){
            types = new LinkedList<>();
            for (Object t : set){
                Type type = BeanUtil.mapToBean((Map<?, ?>) t, Type.class, false, CopyOptions.create());
                types.add(type);
            }
        }
        return types;
    }

    @Override
    public void setType(Type type) {
        redisTemplate.opsForZSet().add("Type",type,(double)type.getId());
        redisTemplate.expire("Type",15, TimeUnit.DAYS);
    }
}
