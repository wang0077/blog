package com.wang.blog.component;

import com.google.common.hash.Funnel;
import com.wang.blog.component.bloomFilter.BloomFilterHelper;
import com.wang.blog.dao.admin.IBlogDao;
import com.wang.blog.dao.admin.ITagDao;
import com.wang.blog.dao.admin.ITypeDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @author wangsiyuan
 */

@Component
public class RedisListener implements ServletContextListener {

    private static final Logger logger =  LoggerFactory.getLogger(RedisListener.class);

    private RedisTemplate<String,Object> redisTemplate;

    private IBlogDao blogDao;

    private ITypeDao typeDao;

    private ITagDao tagDao;

    @Autowired
    public void setTypeDao(ITypeDao typeDao) {
        this.typeDao = typeDao;
    }

    @Autowired
    public void setTagDao(ITagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Autowired
    public void setBlogDao(IBlogDao blogDao) {
        this.blogDao = blogDao;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Object blogSize = redisTemplate.opsForValue().get("BlogSize");
        if(blogSize == null){
            int size = blogDao.countBlog();
            redisTemplate.opsForValue().set("BlogSize",size);
        }
        Object typeSize = redisTemplate.opsForValue().get("TypeSize");
        if(typeSize == null){
            int size = typeDao.countType();
            redisTemplate.opsForValue().set("TypeSize",size);
        }
        Object tagSize = redisTemplate.opsForValue().get("TagSize");
        if(tagSize == null){
            int size = tagDao.countTag();
            redisTemplate.opsForValue().set("TagSize",size);
        }
        logger.info("Redis初始化完成");
    }
}

