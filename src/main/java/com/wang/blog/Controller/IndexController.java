package com.wang.blog.Controller;

import com.wang.blog.Bean.Blog;
import com.wang.blog.Bean.Page;
import com.wang.blog.Exception.NotFindException;
import com.wang.blog.Service.admin.IBlogService;
import com.wang.blog.Service.admin.ITagService;
import com.wang.blog.Service.admin.ITypeService;
import com.wang.blog.Service.admin.impl.SearchBlogServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class IndexController {

    @Autowired
    private IBlogService blogService;
    @Autowired
    private ITypeService typeService;
    @Autowired
    private ITagService tagService;

    private static final String BLOG = "blog";

    private static final String SEARCH = "search";

    @GetMapping("/index/{id}")
    public String index(Model model, Blog blog,@PathVariable("id") int id){
        Page<Blog> page = new Page<>();
        page.setCur_Page(id);
        page.setPage_size(6);
        model.addAttribute("tag",tagService.listTagByCount());
        model.addAttribute("type",typeService.lisTpeByCount());
        model.addAttribute("page",blogService.listBlog(page));
        model.addAttribute("newPage",blogService.listBlogByTime());
        model.addAttribute("count",blogService.getBlogCount());
        return "index";
    }

    @GetMapping("/blog/{id}")
    public String blog(Model model,@PathVariable("id") Long id){
        blogService.UpdateView(id);
        model.addAttribute("blog",blogService.getMarkDownBlog(id));
        model.addAttribute("tag",tagService.getTagByBlogId(id));
        System.out.println(tagService.getTagByBlogId(id));
        System.out.println(blogService.getMarkDownBlog(id));
        return BLOG;
    }

    @PostMapping("/search/{id}")
    public String Search(Model model,
                         @PathVariable("id") int id,
                         @RequestParam String query){
        Page<Blog> page = new Page<>();
        Blog blog = new Blog();
        page.setCur_Page(id);
        blog.setTitle(query);
        page.setPage_size(6);
        SearchBlogServiceImpl searchBlogService = new SearchBlogServiceImpl(blogService);
        model.addAttribute("page",searchBlogService.searchBlog(page,query));
        System.out.println(blogService.searchBlog(page,query));
        model.addAttribute("query",query);
        return SEARCH;
    }

    @GetMapping("/footer/newBlog")
    public String newBlogs(Model model){
        model.addAttribute("newblogs",blogService.listBlogByFoot());
        return "fragments::newblogList";
    }
}
