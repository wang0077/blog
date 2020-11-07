package com.wang.blog.cache.redis.impl;

import com.wang.blog.bean.Blog;
import com.wang.blog.cache.redis.ITagBlogByRedis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author wangsiyuan
 */
@Service
public class BlogTagByRedis implements ITagBlogByRedis {

    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveBlogTag(Long blogId, Long tagId) {
        redisTemplate.opsForList().leftPush("BlogTag:" + blogId,tagId);
        redisTemplate.expire("BlogTag:" + blogId,15, TimeUnit.DAYS);
    }

    @Override
    public void updateBlog(Long blogId, Long tagId) {
        redisTemplate.opsForList().leftPush("BlogTag:" + blogId,tagId);
        redisTemplate.expire("BlogTag:" + blogId,15, TimeUnit.DAYS);
    }

    @Override
    public void delBlogTag(Long blogId, Long tagId) {
        redisTemplate.delete("BlogTag:" + blogId);
        saveBlogTag(blogId,tagId);
    }

    @Override
    public void delBlogTag(Long blogId) {
        redisTemplate.delete("BlogTag:" + blogId);
    }

    @Override
    public List<Integer> listTagByBlogId(Long blogId) {
        List<Object> list = redisTemplate.opsForList().range("BlogTag:" + blogId, 0, -1);
        List<Integer> blogIds = new ArrayList<>();
        if (list != null) {
            for (Object json : list){
                blogIds.add((Integer) json);
            }
        }
        return blogIds;
    }















}
