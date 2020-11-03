package com.wang.blog.cache.redis.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson.JSONObject;
import com.wang.blog.bean.Comment;
import com.wang.blog.cache.redis.ICommentByRedis;
import com.wang.blog.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wangsiyuan
 */
@Service
public class CommentByRedis implements ICommentByRedis {

    RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void saveComment(List<Comment> comments, Long id) {
        for(Comment comment : comments){
            redisTemplate.opsForList().leftPush("Comment:" + id,comment);
        }
    }

    @Override
    public List<Comment> listComment(Long id) {
        List<Object> json = redisTemplate.opsForList().range("Comment:" + id, 0, -1);
        List<Comment> comments = new ArrayList<>();
        if(json != null) {
            for (Object s : json) {
//                System.out.println(s.getClass());
                Comment comment = BeanUtil.mapToBean((Map<?, ?>) s, Comment.class, false, CopyOptions.create());
                comments.add(comment);
            }
        }
        return comments;
    }

    @Override
    public void deleteComment(Long id) {
        redisTemplate.delete("Comment:" + id);
    }
}
