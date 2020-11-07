package com.wang.blog.cache.redis.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.wang.blog.bean.Tag;
import com.wang.blog.cache.redis.ITagByRedis;
import com.wang.blog.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wangsiyuan
 */
@Service
public class TagByRedis implements ITagByRedis {

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
    public List<Tag> listTagByBlogId(int start, int end) {
        Set<Object> set = redisTemplate.opsForZSet().range("Tag", start, end);
        List<Tag> tags = new LinkedList<>();
        if(set != null){
            for (Object t : set){
                Tag tag = BeanUtil.mapToBean((Map<?, ?>) t, Tag.class, false, CopyOptions.create());
                tags.add(tag);
            }
        }
        return tags;
    }

    @Override
    public List<Tag> getTagByBlog(List<Integer> ids) {
        List<Tag> tags = new ArrayList<>();
        for(Integer id : ids){
            Set<Object> json = redisTemplate.opsForZSet().rangeByScore("Tag", id, id);
            if (json != null) {
                for (Object t : json){
                    Tag tag = BeanUtil.mapToBean((Map<?, ?>) t, Tag.class, false, CopyOptions.create());
                    tags.add(tag);
                }
            }
        }
        return tags;
    }

    @Override
    public void updateTag(Long id, Tag tag) {
        deleteTag(id);
        setTag(tag);
    }

    @Override
    public void deleteTag(Long id) {
        redisTemplate.opsForZSet().removeRangeByScore("Tag",id,id);
    }

    @Override
    public Long countTag() {
        return redisTemplate.opsForZSet().zCard("Tag");
    }

    @Override
    public List<Tag> getTagByPage(int start, int end) {
        Set<Object> set = redisTemplate.opsForZSet().range("Tag", start, end);
        List<Tag> tags = new LinkedList<>();
        if(set != null){
            for (Object t : set){
                Tag tag = BeanUtil.mapToBean((Map<?, ?>) t, Tag.class, false, CopyOptions.create());
                tags.add(tag);
            }
        }
        return tags;
    }

    @Override
    public Tag getTagById(Long tagId) {
        Set<Object> set = redisTemplate.opsForZSet().rangeByScore("Tag", tagId, tagId);
        Tag tag = null;
        if (set != null) {
            for (Object t : set){
                @SuppressWarnings(value = "all")
                Map<Object,Object> checkTag = (Map<Object, Object>) t;
                tag = BeanUtil.mapToBean(checkTag, Tag.class, false, CopyOptions.create());
            }
        }
        return tag;
    }

    @Override
    public Tag getTagByName(String name) {
        Set<Object> set = redisTemplate.opsForZSet().range("Tag", 0, -1);
        Tag tag = null;
        if (set != null) {
            for (Object t : set){
                @SuppressWarnings(value = "all")
                Map<Object,Object> checkTag = (Map<Object, Object>) t;
                if(name.equals(checkTag.get("name"))){
                    tag = BeanUtil.mapToBean(checkTag, Tag.class, false, CopyOptions.create());
                }
            }
        }
        return tag;
    }

    @Override
    public List<Tag> listTag() {
        Set<Object> set = redisTemplate.opsForZSet().range("Tag", 0, -1);
        List<Tag> tags = null;
        if(set != null){
            tags = new LinkedList<>();
            for (Object t : set){
                Tag tag = BeanUtil.mapToBean((Map<?, ?>) t, Tag.class, false, CopyOptions.create());
                tags.add(tag);
            }
        }
        return tags;
    }

    @Override
    public void setTag(Tag tag) {
        redisTemplate.opsForZSet().add("Tag",tag,(double)tag.getTag_id());
        redisTemplate.expire("Tag",15, TimeUnit.DAYS);
    }

    @Override
    public void decSize() {
        redisTemplate.opsForValue().decrement("TagSize");
    }

    @Override
    public void addSize() {
        redisTemplate.opsForValue().increment("TagSize");
    }
}
