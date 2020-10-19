package com.wang.blog.controller;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Page;
import com.wang.blog.bean.Type;
import com.wang.blog.service.admin.IBlogService;
import com.wang.blog.service.admin.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TypeShowController {

    private static final String TYPE = "types";

    @Autowired
    private ITypeService typeService;
    @Autowired
    private IBlogService blogService;

    @GetMapping("/type/{typeId}/{id}")
    public String type(Model model, @PathVariable("id") int id,@PathVariable("typeId") Long typedId){
        Page<Blog> page = new Page<>();
        page.setCur_Page(id);
        page.setPage_size(6);
        List<Type> types = typeService.lisTypeByCount();
        if(typedId == -1){
            typedId = types.get(0).getId();
        }
        Blog blog = new Blog();
        System.out.println(typedId);
        blog.setType_id(typedId);
        model.addAttribute("count",typeService.countType());
        model.addAttribute("type",typeService.lisTypeByCount());
//        SearchBlogServiceImpl searchBlogService = new SearchBlogServiceImpl(blogService);
        model.addAttribute("blog",blogService.listBlog(page,blog));
//        System.out.println(blogService.SearchBlog(page,blog).getList().get(0));

        System.out.println(typeService.lisTypeByCount() + "????");
        return TYPE;
    }
}
