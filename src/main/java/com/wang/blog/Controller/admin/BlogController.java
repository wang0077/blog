package com.wang.blog.controller.admin;

import com.wang.blog.bean.Blog;
import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;
import com.wang.blog.bean.User;
import com.wang.blog.service.admin.IBlogService;
import com.wang.blog.service.admin.ITagService;
import com.wang.blog.service.admin.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wangsiyuan
 */
@Controller
@RequestMapping("/admin/blogs")
public class BlogController {

    /**
     * 到编辑页面
     */
    private static final String INPUT = "admin/blog-input";
    /**
     * 到列表页面
     */
    private static final String LIST = "admin/blogs";
    /**
     * 重定向到列表页面
     */
    private static final String REDIRECT_LIST = "redirect:/admin/blogs/1";

    /**
     * 操作信息
     */
    private static final String MESSAGE = "message";

    private IBlogService service;

    private ITypeService typeService;

    private ITagService tagService;

    @Autowired
    public void setService(IBlogService service) {
        this.service = service;
    }

    @Autowired
    public void setTypeService(ITypeService typeService) {
        this.typeService = typeService;
    }

    @Autowired
    public void setTagService(ITagService tagService) {
        this.tagService = tagService;
    }

    /**
     *
     * @param blog  存放blog内容，用来检索内容
     * @param id    页面的页码数，配合Page检索页面内容
     * @return      回到显示列表页面Blogs
     */
    @GetMapping("/{id}")
    public String list(Model model, Blog blog,
                       @PathVariable("id") Integer id,
                       HttpServletRequest request,
                       HttpSession session){
        if(session.getAttribute(MESSAGE) != null){
            request.setAttribute("message",session.getAttribute("message"));
            session.removeAttribute("message");
        }
        Page<Blog> page = new Page<>();
        page.setCur_Page(id);
        page.setPage_size(5);
        blog.setRecommend(true);
        page.setList(service.getBlogOnPage(page));
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",page);
        return LIST;
    }

    /**
     *
     * 搜索功能
     */
    @PostMapping("/search")
    public String search(Model model, Blog blog, @RequestParam("page") Integer cur_page){
        System.out.println(cur_page);
        if("".equals(blog.getTitle())){
            blog.setTitle(null);
        }
        Page<Blog> page = new Page<>();
        if(cur_page == null) {
            cur_page = 1;
        }
        page.setCur_Page(cur_page);
        page.setPage_size(5);
        System.out.println(blog);
        model.addAttribute("page",service.listBlog(page,blog));
        return "admin/blogs :: blogList";
    }

    /**
     *
     * 跳转到新增页面
     */
    @GetMapping("/input")
    public String toInput(Model model){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
        model.addAttribute("blog",new Blog());
        return INPUT;
    }

    /**
     * 进入修改页面并带入原有博客内容
     * @param id    博客的主键用来查询博客相关内容
     * @return      进入博客修改页
     */
    @GetMapping("/input/{id}")
    public String toInput(Model model, @PathVariable("id") Long id){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listType());
        Blog blog = service.getBlog(id);
        blog.setTagList(tagService.getTagByBlogId(id));
        blog.setTag_id(service.getTagIds(blog.getTagList()));
        blog.setType(typeService.getType(blog.getType_id()));
        System.out.println(blog);
        model.addAttribute("blog",blog);
        return INPUT;
    }

    /**
     *
     * 通过博客的id来判断是修改还是新增
     */
    @PostMapping("/blogs")
    public String post(Blog blog, @RequestParam("Tag") String tagId, HttpSession session){
        List<Tag> tagSearch = tagService.getTagSearch(tagId);
        blog.setTagList(tagSearch);
        blog.setType(typeService.getType(blog.getType().getId()));
        User user = (User) session.getAttribute("user");
        blog.setUser(user);
        blog.setUser_id(user.getId());
        //修改操作
        if(blog.getId() != null){
            service.updateBlog(blog.getId(),blog);
        }else {
            //新增操作
            Blog saveBlog = service.saveBlog(blog, tagSearch);
            if(saveBlog == null){
                session.setAttribute("message","操作失败");
            }else {
                session.setAttribute("message","操作成功");
            }
        }
        return REDIRECT_LIST;
    }

    /**
     * 删除博客
     *
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id){
        service.deleteBlog(id);
        return REDIRECT_LIST;
    }

}
