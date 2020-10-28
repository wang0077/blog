package com.wang.blog.cache.redis.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Type;
import com.wang.blog.cache.redis.ITypeByRedis;
import com.wang.blog.util.BeanAndMap;
import com.wang.blog.util.RedisUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    public Type getTypeById(int typeId) {
        return null;
    }

    @Override
    public Type getTypeByName(String name) {
        return null;
    }

    @Override
    public List<Type> listType() {
        Set<String> keys = redisUtil.keys("Type:*");
        System.out.println(keys.size());
        for (String s : keys){
            Type type = new Type();
            Map<Object, Object> map = redisUtil.hGetAll(s);
            Map<String,Object> map1 = new HashMap<>();

            Type type1 = new Type();
            Blog blog = new Blog();
            type.getBlogList().add(blog);
            type.setCount(1);
            type.setId((long) 223);
            type.setName("123");
            Map<String, Object> map2 = new HashMap<>();
            map2.put("id",type.getId());
            map2.put("name",type.getName());
            map2.put("count",type.getCount());
            map2.put("blogList",type.getBlogList());



//            System.out.println(map.get("blogList") + "???");
            for (Map.Entry<Object,Object> entry : map.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
                map1.put((String)entry.getKey(),entry.getValue());
            }
            System.out.println("=====================");
            for (Map.Entry<String,Object> entry : map2.entrySet()){
                System.out.println(entry.getKey() + " : " + entry.getValue());
            }

            try {
                BeanUtils.populate(type,map2);
                System.out.println(type);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean setType(Type type) {
        HashMap<String, String> map = BeanAndMap.toMap(type);
        redisUtil.hPutAll("Type:" + type.getId(),map);
        redisUtil.expire("Type:" + type.getId(),15, TimeUnit.DAYS);
        return true;
    }
}
