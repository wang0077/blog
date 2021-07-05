package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;
import com.wang.blog.cache.redis.ITagBlogByRedis;
import com.wang.blog.cache.redis.ITagByRedis;
import com.wang.blog.cache.redis.impl.BlogByRedis;
import com.wang.blog.dao.admin.ITagDao;
import com.wang.blog.exception.NotFindException;
import com.wang.blog.service.admin.ITagService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.sound.midi.Soundbank;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangsiyuan
 */
@Service
public class TagServiceImpl implements ITagService {


    private ITagDao tagDao;

    private ITagByRedis tagRedis;

    private BlogByRedis blogByRedis;

    private ITagBlogByRedis tagBlogByRedis;

    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    public void setTagBlogByRedis(ITagBlogByRedis tagBlogByRedis) {
        this.tagBlogByRedis = tagBlogByRedis;
    }

    @Autowired
    public void setBlogByRedis(BlogByRedis blogByRedis) {
        this.blogByRedis = blogByRedis;
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setTagRedis(ITagByRedis tagRedis) {
        this.tagRedis = tagRedis;
    }

    @Autowired
    public void setRedis(ITagByRedis redis) {
        this.tagRedis = redis;
    }

    @Autowired
    public void setTagDao(ITagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public void saveTag(String name) {
        tagDao.saveTag(name);
        Tag tag = tagDao.getTagByName(name);
        tagRedis.setTag(tag);
        tagRedis.addSize();
    }

    @Override
    public Tag getTagById(Long id) {
        Tag tag = tagRedis.getTagById(id);
        if(tag == null){
            tag = tagDao.getTagById(id);
            tagRedis.setTag(tag);
        }
        return tag;
    }

    @Override
    public Tag getTagByName(String name) {
        Tag tag = tagRedis.getTagByName(name);
        if(tag == null){
            tag = tagDao.getTagByName(name);
            if(tag != null) {
                tagRedis.setTag(tag);
            }
        }
        return tag;
    }

    @Override
    public Page<Tag> listTag(@NotNull Page<Tag> page) {
        page.setPage_count(Math.toIntExact(tagRedis.countTag()));
//        计算当前一页存放N条情况下,总共有多少页
        page.setPage_tot(page.getPage_count() / page.getPage_size() + page.getPage_count() / page.getPage_size() == 0 ? 0 :1);
//        如果分页不足一页
        if(page.getPage_tot() == 0){
            page.setPage_tot(1);
        }
//        计算当前页码需要的博客区间
        int start = page.getPage_size() * (page.getCur_Page() - 1);
        checkTagSize();
        page.setList(tagRedis.getTagByPage(start,page.getPage_size()));
        return page;
    }

    @Override
    public Long getTagCount(){
        checkTagSize();
        return tagRedis.countTag();
    }

    @Override
    public List<Tag> countListByTag() {
        checkTagSize();
        return tagRedis.listTag();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateTag(Long id,String name) {
//        通过Id获取标签
        Tag tag = tagDao.getTagById(id);
        if(tag == null){
            throw new NotFindException();
        }
        tagDao.updateTag(id, name);
        tag.setTag_name(name);
        tagRedis.updateTag(id,tag);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long id) {
        tagDao.deleteTag(id);
        tagRedis.deleteTag(id);
        tagRedis.decSize();
    }

    @Override
    public List<Tag> listTag(){
        checkTagSize();
        return tagRedis.listTag();
    }

    @Override
    public List<Tag> getTagSearch(String ids) {
//        将前端传递的标签的Id("1","2","3"这种形式)进行处理
        String[] split = ids.split(",");
        List<Integer> id = new ArrayList<>();
//        转化Id为int为接下来的搜索做准备
        for (String s : split) {
            id.add(Integer.parseInt(s));
        }
        checkTagSize();
        List<Tag> tags = tagRedis.getTagByBlog(id);
        if(tags.size() != id.size()){
            tags = tagDao.getTagByBlog(id);
            checkTagSize();
        }
        return tags;
    }

    @Override
    public List<Tag> getTagByBlogId(Long id) {
        checkTagSize();
        List<Integer> tIds = tagBlogByRedis.listTagByBlogId(id);
        return tagRedis.getTagByBlog(tIds);
//        return tagDao.getTagByBlogId(id);
    }

    private void checkTagSize(){
        @SuppressWarnings("all")
        int daoCount = (int)redisTemplate.opsForValue().get("TagSize");
        Long redisCount = tagRedis.countTag();
        if(redisCount != daoCount){
            List<Tag> tags = tagDao.listTagAll();
            for(Tag tag : tags){
                tagRedis.setTag(tag);
            }
        }
    }
}
