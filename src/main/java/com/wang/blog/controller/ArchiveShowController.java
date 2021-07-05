package com.wang.blog.controller;

import com.wang.blog.service.admin.IBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wangsiyuan
 */
@Controller
public class ArchiveShowController {

    private final static String ARCHIVE = "archives";

    private IBlogService blogService;

    @Autowired
    public void setBlogService(IBlogService blogService) {
        this.blogService = blogService;
    }

    /**
     *
     * 归档页面
     */
    @GetMapping("/archive")
    public String archive(Model model){
        model.addAttribute("count",blogService.getBlogCount());
        model.addAttribute("archive",blogService.listBlogByYear());
        return ARCHIVE;
    }
}
