package com.wang.blog.Controller;

import com.wang.blog.Bean.Blog;
import com.wang.blog.Bean.Page;
import com.wang.blog.Bean.Tag;
import com.wang.blog.Service.admin.IBlogService;
import com.wang.blog.Service.admin.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class TagShowController {

    private static final String TAG = "tags";
    private static final int Page_Size = 8;
    @Autowired
    private ITagService tagService;
    @Autowired
    private IBlogService blogService;

    @GetMapping("/tag/{tagId}/{id}")
    public String tag(Model model,@PathVariable("tagId") Long tagId, @PathVariable("id") int id){
        List<Tag> tags = tagService.listTagByCount();
        Page<Blog> page = new Page<>();
        page.setPage_size(Page_Size);
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
