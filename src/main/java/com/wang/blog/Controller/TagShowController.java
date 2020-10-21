package com.wang.blog.controller;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;
import com.wang.blog.service.admin.IBlogService;
import com.wang.blog.service.admin.ITagService;
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
public class TagShowController {

    private static final String TAG = "tags";
    private static final int PAGE_SIZE = 8;

    private ITagService tagService;

    private IBlogService blogService;

    @Autowired
    public void setTagService(ITagService tagService) {
        this.tagService = tagService;
    }

    @Autowired
    public void setBlogService(IBlogService blogService) {
        this.blogService = blogService;
    }

    /**
     * 获取标签id为tagId的博客的第id页内容
     * @param tagId 标签id
     * @param id 第几页
     */
    @GetMapping("/tag/{tagId}/{id}")
    public String tag(Model model,@PathVariable("tagId") Long tagId, @PathVariable("id") int id){
        List<Tag> tags = tagService.countListByTag();
        Page<Blog> page = new Page<>();
        page.setPage_size(PAGE_SIZE);
        page.setCur_Page(id);
        model.addAttribute("tag",tags);
        if(tagId == -1){
            tagId = tags.get(0).getTag_id();
        }
        model.addAttribute("count",tagService.getTagCount());
        model.addAttribute("page",blogService.listBlogByTag(page,tagId));
        model.addAttribute("tagId",tagId);
        System.out.println(blogService.listBlogByTag(page,tagId).getList());
        return TAG;
    }
}
