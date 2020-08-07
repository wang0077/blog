package com.wang.blog.Controller.admin;

import com.wang.blog.Bean.Page;
import com.wang.blog.Bean.Tag;
import com.wang.blog.Bean.Type;
import com.wang.blog.Service.admin.ITagService;
import com.wang.blog.Service.admin.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/tags")
public class TagController {
    @Autowired
    private ITagService service;

    @GetMapping("/{page}")
    public String list(@PathVariable("page") int cur_page,
                       HttpSession session,
                       Model model){
        Page<Tag> page = new Page<>();
        page.setCur_Page(cur_page);
        page.setPage_size(10);
        Page<Tag> listType = service.listTag(page);
        System.out.println(page.getPage_tot());
        if(session.getAttribute("operation") != null){
            if(session.getAttribute("operation").equals("update")){
                model.addAttribute("message","恭喜，修改成功!");
                session.removeAttribute("operation");
            }else if(session.getAttribute("operation").equals("delete")){
                model.addAttribute("message","恭喜，删除成功!");
                session.removeAttribute("operation");
            }else {
                model.addAttribute("message","恭喜，添加成功!");
                session.removeAttribute("operation");
            }
        }
        model.addAttribute("page",listType);
        System.out.println(listType.getList());
        return "admin/tags";
    }


    //修改分类标签
    @PostMapping("/{id}")
    public String updateType(@PathVariable("id") Long id,
                             @RequestParam("name") String name,
                             HttpSession session){
        service.updateTag(id,name);
        session.setAttribute("operation","update");
        return "redirect:/admin/tags/1";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         HttpSession session){
        service.deleteTag(id);
        session.setAttribute("operation","delete");
        return "redirect:/admin/tags/1";
    }


    //跳转到修改分类标签的页面
    @GetMapping("/input/{id}")
    public String input(@PathVariable("id") Long id,Model model){
        Tag tag = service.getTagById(id);
        model.addAttribute("tag",tag);
        return "admin/tags-input";
    }

    @GetMapping("/input")
    public String toAddInput(Model model,HttpSession session){
        if(session.getAttribute("msg") != null){
            model.addAttribute("msg","请勿重复添加标签!");
            session.removeAttribute("msg");
        }
        model.addAttribute("tag",new Tag());
        return "admin/tags-input";
    }

    @PostMapping("/addType")
    public String addInput(@RequestParam("name") String name,
                           HttpSession session){
        System.out.println(name);
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
