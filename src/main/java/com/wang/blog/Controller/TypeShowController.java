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

/**
 * @author wangsiyuan
 */
@Controller
public class TypeShowController {

    private static final String TYPE = "types";

    private ITypeService typeService;

    private IBlogService blogService;

    @Autowired
    public void setTypeService(ITypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setBlogService(IBlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * 获取分类id为typedId的博客的第id页内容
     * @param typedId 标签id
     * @param id 第几页
     */
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
        model.addAttribute("blog",blogService.listBlog(page,blog));
        return TYPE;
    }
}
