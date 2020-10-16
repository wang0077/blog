package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Page;
import com.wang.blog.service.admin.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author wangsiyuan
 */
public class SearchBlogServiceImpl extends BlogServiceImpl {

    private IBlogService blogService;

    public SearchBlogServiceImpl(IBlogService blogService) {
        this.blogService = blogService;
    }

    @Override
    public Page<Blog> searchBlog(Page<Blog> page, String query) {
        query = Search(query);
        return blogService.searchBlog(page, query);
    }

    @Override
    public Page<Blog> listBlog(Page<Blog> page, Blog blog) {
        Search(blog);
        return blogService.listBlog(page, blog);
    }

    private String Search(String query){
        return "%" + query + "%";
    }

    private void Search(Blog blog){
        if(blog.getTitle() != null){
            blog.setTitle("%" + blog.getTitle() + "%");
        }
    }
}
