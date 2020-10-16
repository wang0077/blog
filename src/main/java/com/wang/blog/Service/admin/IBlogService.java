package com.wang.blog.service.admin;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface IBlogService {

    public String getTagIds(List<Tag> tags);

    public Blog getBlog(Long id);

    public Blog getMarkDownBlog(Long id);

    public void UpdateView(Long id);

    public Page<Blog> searchBlog(Page<Blog> page,String query);

    public Page<Blog> listBlog(Page<Blog> page,Blog blog);

    public Page<Blog> listBlogByTag(Page<Blog> page,Long TagId);

    public Page<Blog> listBlog(Page<Blog> page);

    public List<Blog> listBlogByTime();

//    public List<Blog> listBlogByFoot();

    public int getBlogCount();

    public List<Blog> getBlogAll(Page<Blog> page);

    public Blog saveBlog(Blog blog,List<Tag> tags);

    public Date format_toString(Date time);

    public Date format_toDate(String time);

    public void updateBlog(Long id,Blog blog);

    public void deleteBlog(Long id);

    public void DeleteComment(Long id);

    Map<String,List<Blog>> listBlogByYear();
}
