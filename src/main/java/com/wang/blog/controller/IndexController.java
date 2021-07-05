package com.wang.blog.controller;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Page;
import com.wang.blog.exception.NotFindException;
import com.wang.blog.service.admin.IBlogService;
import com.wang.blog.service.admin.ITagService;
import com.wang.blog.service.admin.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * @author wangsiyuan
 */
@Controller
public class IndexController {


    private IBlogService blogService;

    private ITypeService typeService;

    private ITagService tagService;

    @Autowired
    public void setBlogService(IBlogService blogService) {
        this.blogService = blogService;
    }

    @Autowired
    public void setTypeService(ITypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setTagService(ITagService tagService) {
        this.tagService = tagService;
    }

    private static final String BLOG = "blog";

    private static final String SEARCH = "search";

    /**
     *
     * 获取第几页的博客
     */
    @GetMapping("/index/{id}")
    public String index(Model model, Blog blog,@PathVariable("id") int id){
        Page<Blog> page = new Page<>();
        page.setCur_Page(id);
        page.setPage_size(6);
        model.addAttribute("tag",tagService.countListByTag());
        model.addAttribute("type",typeService.lisTypeByCount());
        model.addAttribute("page",blogService.listBlog(page));
        model.addAttribute("newPage",blogService.listBlogByTime());
        model.addAttribute("count",blogService.getBlogCount());
        return "index";
    }

    /**
     * 获取博客ID = id的博客
     * @param id 博客的id
     */
    @GetMapping("/blog/{id}")
    public String blog(Model model,@PathVariable("id") Long id){
        Blog blog = blogService.getMarkDownBlog(id);
        model.addAttribute("blog", blog);
        model.addAttribute("tag", tagService.getTagByBlogId(id));
        blogService.updateView(id);
        return BLOG;
    }

    /**
     * 获取搜索得到的第id页的博客
     * @param id 第几页
     * @param query 搜索的内容
     */
    @PostMapping("/search/{id}")
    public String search(Model model,
                         @PathVariable("id") int id,
                         @RequestParam String query){
        Page<Blog> page = new Page<>();
        Blog blog = new Blog();
        page.setCur_Page(id);
        blog.setTitle(query);
        page.setPage_size(6);
        model.addAttribute("page",blogService.searchBlogByString(page,query));
        model.addAttribute("query",query);
        return SEARCH;
    }

}
