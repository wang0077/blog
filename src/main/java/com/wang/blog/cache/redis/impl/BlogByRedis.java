package com.wang.blog.cache.redis.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.date.DateUtil;
import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Tag;
import com.wang.blog.cache.redis.IBlogByRedis;
import com.wang.blog.cache.redis.ICommentByRedis;
import com.wang.blog.cache.redis.ITagBlogByRedis;
import com.wang.blog.cache.redis.ITypeByRedis;
import org.jetbrains.annotations.TestOnly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.swing.text.BadLocationException;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author wangsiyuan
 */
@Service
public class BlogByRedis implements IBlogByRedis {

    RedisTemplate<String,Object> redisTemplate;

    ITagBlogByRedis tagBlogByRedis;

    ITypeByRedis typeByRedis;

    ICommentByRedis commentByRedis;

    @Autowired
    public void setCommentByRedis(ICommentByRedis commentByRedis) {
        this.commentByRedis = commentByRedis;
    }

    @Autowired
    public void setTypeByRedis(ITypeByRedis typeByRedis) {
        this.typeByRedis = typeByRedis;
    }

    @Autowired
    public void setTagBlogByRedis(ITagBlogByRedis tagBlogByRedis) {
        this.tagBlogByRedis = tagBlogByRedis;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public List<Blog> listBlog() {
        Set<String> set = redisTemplate.keys("Blog:*");
        List<Blog> blogs = new ArrayList<>();
        if (set != null) {
            for(String key : set){
                Object json = redisTemplate.opsForValue().get(key);
                Blog blog = BeanUtil.mapToBean((Map<?, ?>) json, Blog.class, false, CopyOptions.create());
                blogs.add(blog);
            }
        }
        return blogs;
    }

    @Override
    public void saveBlog(Blog blog) {
        redisTemplate.opsForValue().set("Blog:" + blog.getId(),blog);
        redisTemplate.expire("Blog:" + blog.getId(),15, TimeUnit.DAYS);
        List<Tag> tags = blog.getTagList();
        Long blogId = blog.getId();
        List<Integer> list = tagBlogByRedis.listTagByBlogId(blogId);
        if(list.size() != 0){
            tagBlogByRedis.delBlogTag(blogId);
        }
        for(Tag tag : tags){
            tagBlogByRedis.saveBlogTag(blogId,tag.getTag_id());
        }
    }

    @Override
    public Blog getBlogById(Long id) {
        Object json = redisTemplate.opsForValue().get("Blog:" + id);
        return BeanUtil.mapToBean((Map<?, ?>) json, Blog.class, false, CopyOptions.create());
    }

    @Override
    public Blog getBlogById(Integer id) {
        Object json = redisTemplate.opsForValue().get("Blog:" + id);
        return BeanUtil.mapToBean((Map<?, ?>) json, Blog.class, false, CopyOptions.create());
    }

    @Override
    public void updateView(Long id) {

    }

    @Override
    public List<Blog> listBlogByTag(Long tagId) {
        List<Blog> blogs = listBlogWithUpTime();
        List<Blog> reBlogs = new ArrayList<>();
        for (Blog blog :blogs){
            List<Tag> tags = blog.getTagList();
            for (Tag tag :tags){
                if(tagId.equals(tag.getTag_id())){
                    reBlogs.add(blog);
                    break;
                }
            }
        }
        return reBlogs;
    }

    @Override
    public List<Blog> listBlogWithUpTime() {
        List<Blog> blogs = listBlog();
        blogs.sort((o1, o2) -> o1.getUpdateTime().before(o2.getUpdateTime()) ? 0 : -1);
        return blogs;
    }

    @Override
    public List<Blog> listBlogWithUpTimeOnPage(int start,int length){
        return  listBlogOnPage(start,length);
    }

    @Override
    public int countBlogByTag(Long tid) {
        return listBlogByTag(tid).size();
    }

    @Override
    public List<Blog> listBlogByType(Long tid) {
        List<Blog> blogs = listBlogWithUpTime();
        List<Blog> reBlogs = new ArrayList<>();
        for (Blog blog : blogs){
            if(tid.equals(blog.getType_id())){
                reBlogs.add(blog);
            }
        }
        return reBlogs;
    }

    @Override
    public int countBlogByType(Long tid) {
        return listBlogByType(tid).size();
    }

    @Override
    public int countByBlog() {
        Set<String> keys = redisTemplate.keys("Blog:*");
        int size = 0;
        if(keys != null){
            size = keys.size();
        }
        return size;
    }

    @Override
    public void updateBlog(Blog blog) {
        redisTemplate.delete("Blog:" + blog.getId());
        saveBlog(blog);
    }

    @Override
    public void delBlog(Long blogId) {
        redisTemplate.delete("Blog:" + blogId);
        typeByRedis.deleteType(blogId);
        tagBlogByRedis.delBlogTag(blogId);
        commentByRedis.deleteComment(blogId);
    }

    @Override
    public List<String> getYear(){
        List<Blog> blogs = listBlog();
        Set<String> years = new HashSet<>();
        for (Blog blog : blogs){
            Date year = blog.getUpdateTime();
            String tranYear = DateUtil.format(year,"YYYY");
            years.add(tranYear);
        }
        return new ArrayList<>(years);
    }

    @Override
    public List<Blog> getBlogByYear(String year) {
        List<Blog> blogs = new ArrayList<>();
        List<Blog> blogList = listBlogWithUpTime();

        for (Blog blog : blogList){
            Date upTime = blog.getUpdateTime();
            String blogYear = DateUtil.format(upTime,"YYYY");
            if(blogYear.equals(year)){
                blogs.add(blog);
            }
        }
        return blogs;
    }

    @Override
    public List<Blog> listBlogOnPage(int start, int length) {
        List<Blog> blogs = listBlogWithUpTime();
        if(start + length > blogs.size() - 1){
            length = blogs.size() - start;
        }
        return blogs.subList(start,start + length);
    }

    @Override
    public List<Blog> listBlogOnPage(int start, int length, List<Blog> blogs) {
        if(start + length > blogs.size() - 1){
            length = blogs.size() - start;
        }
        return blogs.subList(start,start + length);
    }

    @Override
    public void addSize() {
        redisTemplate.opsForValue().decrement("BlogSize");
    }

    @Override
    public void decSize() {
        redisTemplate.opsForValue().increment("BlogSize");
    }


}


