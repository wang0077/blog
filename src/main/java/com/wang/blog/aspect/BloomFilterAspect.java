package com.wang.blog.aspect;

import com.wang.blog.bean.Blog;
import com.wang.blog.component.RedisListener;
import com.wang.blog.component.bloomFilter.BloomFilterHelper;
import com.wang.blog.component.bloomFilter.BloomFilterServer;
import com.wang.blog.exception.NotFindException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author wangsiyuan
 */
@Aspect
@Component
public class BloomFilterAspect {

    private static final Logger logger =  LoggerFactory.getLogger(RedisListener.class);

    private BloomFilterServer bloomFilterServer;

    private BloomFilterHelper<String> bloomFilterHelper;

    private final static String BLOGFILTER = "BlogBloom";

    @Pointcut("execution(* com.wang.blog.cache.redis.impl.BlogByRedis.saveBlog(..))")
    public void blogAdd(){}

    @Pointcut("execution(* com.wang.blog.service.admin.impl.BlogServiceImpl.get*Blog(..))")
    public void blogCheck(){}

    @Pointcut("execution(* com.wang.blog.cache.redis.impl.TypeByRedis.setType(..))")
    public void typeAdd(){}

    @Pointcut("execution(* com.wang.blog.service.admin.impl.TypeServiceImpl.getType(..))")
    public void typeCheck(){}

    @Autowired
    public void setBloomFilterHelper(BloomFilterHelper<String> bloomFilterHelper) {
        this.bloomFilterHelper = bloomFilterHelper;
    }

    @Autowired
    public void setBloomFilterServer(BloomFilterServer bloomFilterServer) {
        this.bloomFilterServer = bloomFilterServer;
    }

    @Before("blogAdd()")
    public void blogAddBloom(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        Blog blog = (Blog) args[0];
        bloomFilterServer.addByBloomFilter(bloomFilterHelper,BLOGFILTER,"Blog" + blog.getId());
    }

    @Around(value = "blogCheck()")
    public Blog blogCheck(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        boolean result = bloomFilterServer.includeByBloomFilter(bloomFilterHelper, BLOGFILTER, "Blog" + args[0].toString());
        Blog blog = new Blog();
        if(result){
            blog = (Blog) joinPoint.proceed();
        }else{
            throw new NotFindException("该博客不存在！");
        }
        return blog;
    }

}
