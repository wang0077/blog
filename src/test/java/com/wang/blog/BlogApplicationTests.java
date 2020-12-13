package com.wang.blog;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Type;
import com.wang.blog.cache.redis.IBlogByRedis;
import com.wang.blog.cache.redis.ICommentByRedis;
import com.wang.blog.cache.redis.ITypeByRedis;
import com.wang.blog.cache.redis.impl.TypeByRedis;
import com.wang.blog.dao.admin.IBlogDao;
import com.wang.blog.util.RedisUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@EnableCaching
class BlogApplicationTests {

//    @Autowired
//    RedisTemplate<String,Object> template;
//
//    @Autowired
//    RedisUtil redisUtil;
//
//    @Autowired
//    ITypeByRedis redis;
//
//    @Autowired
//    ICommentByRedis commentByRedis;
//
//    @Autowired
//    IBlogByRedis blogByRedis;
//
//    @Autowired
//    IBlogDao blogDao;
//
//    @Autowired
//    RedisTemplate<String,Object> redisTemplate;
//
//    @Autowired
//    TypeByRedis typeByRedis;
//
//
//
//
//    @Test
//    public void Redis(){
//        template.opsForValue().set("123","@#2");
//    }
//
//
//    @Test
//    public void test() throws InvocationTargetException, IllegalAccessException {
//        Type type = new Type();
//        Blog blog = new Blog();
//        type.getBlogList().add(blog);
//        type.setCount(1);
//        type.setId((long) 223);
//        type.setName("123");
//        Map<String, Object> map = new HashMap<>();
//        map.put("id",type.getId());
//        map.put("name",type.getName());
//        map.put("count",type.getCount());
//        map.put("blogList",type.getBlogList());
//        System.out.println(map);
//        Type type1 = new Type();
//        BeanUtils.populate(type1,map);
//        System.out.println(type1);
//    }
//
//    @Test
//    public void TestRedis() throws InstantiationException, IllegalAccessException {
//        Type type = new Type();
//        type.setId((long)123);
//        type.setName("23123");
//        type.setCount(231);
//        Blog blog = new Blog();
//        type.getBlogList().add(blog);
//        template.opsForHash().put("Type:" + type.getId(),type.getId(),type);
//        redisUtil.expire("Type:" + type.getId(),15, TimeUnit.DAYS);
//    }
//
//
//    @Test
//    public void Test() throws IllegalAccessException, InstantiationException, NoSuchFieldException, ClassNotFoundException {
//        Map<Object, Object> map = redisUtil.hGetAll("Type:123");
//        System.out.println(template.opsForHash().get("Type:123",123));
//        HashMap<Object,Object> o = (HashMap<Object,Object>)template.opsForHash().get("Type:123", 123);
//        for (Map.Entry<Object,Object> entry : o.entrySet()){
//            System.out.println(entry.getKey() + " : " + entry.getValue() + " : " + entry.getValue().getClass());
//        }
////        for(Map.Entry<Object,Object> entry : map.entrySet()){
////            System.out.println(entry.getValue().toString());
////            Type type = JSONObject.parseObject(entry.getValue().toString(), Type.class);
////            System.out.println(type);
////        }
//    }
//
//    public <T> T and(T name){
//        return name;
//    }
//
//    @Test
//    public void jsonToBean() throws JsonProcessingException {
//        System.out.println(redis.listType());
//    }
//
//    @Test
//    public void TestComment(){
//        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
//        String encode = bCryptPasswordEncoder.encode("123");
//        System.out.println(encode);
//    }



}
