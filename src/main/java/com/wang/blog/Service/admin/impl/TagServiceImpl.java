package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;
import com.wang.blog.dao.admin.ITagDao;
import com.wang.blog.exception.NotFindException;
import com.wang.blog.service.admin.ITagService;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wangsiyuan
 */
@Service
public class TagServiceImpl implements ITagService {


    private ITagDao tagDao;

    @Autowired
    public void setTagDao(ITagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public void saveTag(String name) {
        tagDao.saveTag(name);
    }

    @Override
    public Tag getTagById(Long id) {
        return tagDao.getTagById(id);
    }

    @Override
    public Tag getTagByName(String name) {
        return tagDao.getTagByName(name);
    }

    @Override
    public Page<Tag> listTag(@NotNull Page<Tag> page) {
        page.setPage_count(tagDao.countTag());
//        计算当前一页存放N条情况下,总共有多少页
        page.setPage_tot(page.getPage_count() / page.getPage_size() + page.getPage_count() / page.getPage_size() == 0 ? 0 :1);
//        如果分页不足一页
        if(page.getPage_tot() == 0){
            page.setPage_tot(1);
        }
//        计算当前页码需要的博客区间
        int start = page.getPage_size() * (page.getCur_Page() - 1);
        page.setList(tagDao.listTag(start,page.getPage_size()));
        return page;
    }

    @Override
    public int getTagCount(){
        return tagDao.countTag();
    }

    @Override
    public List<Tag> countListByTag() {
        return tagDao.countTagByBlog(0,20);
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
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteTag(Long id) {
        tagDao.deleteTag(id);
    }

    @Override
    public List<Tag> listTag(){
        return tagDao.listTagAll();
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
        return tagDao.getTagByBlog(id);
    }

    @Override
    public List<Tag> getTagByBlogId(Long id) {
        return tagDao.getTagByBlogId(id);
    }
}
