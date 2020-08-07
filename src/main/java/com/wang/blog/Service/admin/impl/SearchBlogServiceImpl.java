package com.wang.blog.Service.admin.impl;

import com.wang.blog.Bean.Blog;
import com.wang.blog.Bean.Page;
import com.wang.blog.Service.admin.IBlogService;

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
        if(blog.getTitle() != null)
            blog.setTitle("%" + blog.getTitle() + "%");
    }
}
