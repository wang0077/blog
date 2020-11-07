package com.wang.blog.controller.admin;

import com.wang.blog.bean.Page;
import com.wang.blog.bean.Tag;
import com.wang.blog.service.admin.ITagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * @author wangsiyuan
 */
@Controller
@RequestMapping("/admin/tags")
public class TagController {

    private ITagService service;

    /**
     * 操作的回调信息储存在session的名字
     */
    private static final String OPERATION = "operation";

    /**
     * 更新操作
     */
    private static final String UPDATE = "update";

    /**
     * 删除操作
     */
    private static final String DELETE = "delete";

    /**
     * 新增错误的回调信息
     */
    private static final String MSG = "msg";

    @Autowired
    public void setService(ITagService service) {
        this.service = service;
    }

    /**
     *
     * 获取当前页的标签
     * @param curPage 当前第几页
     */
    @GetMapping("/{page}")
    public String list(@PathVariable("page") int curPage,
                       HttpSession session,
                       Model model){
        Page<Tag> page = new Page<>();
        page.setCur_Page(curPage);
        page.setPage_size(10);
        Page<Tag> listType = service.listTag(page);
        if(session.getAttribute(OPERATION) != null){
            if(UPDATE.equals(session.getAttribute(OPERATION))){
                model.addAttribute("message","恭喜，修改成功!");
                session.removeAttribute(OPERATION);
            }else if(DELETE.equals(session.getAttribute(OPERATION))){
                model.addAttribute("message","恭喜，删除成功!");
                session.removeAttribute(OPERATION);
            }else {
                model.addAttribute("message","恭喜，添加成功!");
                session.removeAttribute(OPERATION);
            }
        }
        model.addAttribute("page",listType);
        return "admin/tags";
    }



    /**
     *
     * 修改标签
     */
    @PostMapping("/{id}")
    public String updateType(@PathVariable("id") Long id,
                             @RequestParam("name") String name,
                             HttpSession session){
        service.updateTag(id,name);
        session.setAttribute("operation","update");
        return "redirect:/admin/tags/1";
    }

    /**
     *
     * 删除标签
     */
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         HttpSession session){
        service.deleteTag(id);
        session.setAttribute("operation","delete");
        return "redirect:/admin/tags/1";
    }


    /**
     *
     * 跳转到修改标签的页面
     */
    @GetMapping("/input/{id}")
    public String input(@PathVariable("id") Long id,Model model){
        Tag tag = service.getTagById(id);
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

    /**
     *
     * 跳转到新建标签的页面
     */
    @GetMapping("/input")
    public String toAddInput(Model model,HttpSession session){
        if(session.getAttribute(MSG) != null){
            model.addAttribute("msg","请勿重复添加标签!");
            session.removeAttribute("msg");
        }
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    /**
     *
     * 新增标签
     */
    @PostMapping("/addTag")
    public String addInput(@RequestParam("name") String name,
                           HttpSession session){
        Tag tagByName = service.getTagByName(name);
        if(tagByName != null){
            session.setAttribute("msg","");
            return "redirect:/admin/tags/input";
        }
        service.saveTag(name);
        session.setAttribute("operation","delete");
        return "redirect:/admin/tags/1";
    }
}
