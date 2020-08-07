package com.wang.blog.Controller.admin;

import com.wang.blog.Bean.Page;
import com.wang.blog.Bean.Type;
import com.wang.blog.Service.admin.ITypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin/types")
public class TypeController {

    @Autowired
    private ITypeService service;

    @GetMapping("/{page}")
    public String list(@PathVariable("page") int cur_page,
                       HttpSession session,
                       Model model){
        Page<Type> page = new Page<>();
        page.setCur_Page(cur_page);
        page.setPage_size(10);
        Page<Type> listType = service.listType(page);
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
        return "admin/types";
    }


    //修改分类标签
    @PostMapping("/{id}")
    public String updateType(@PathVariable("id") Long id,
                             @RequestParam("name") String name,
                             HttpSession session){
        service.updateType(id,name);
        session.setAttribute("operation","update");
        return "redirect:/admin/types/1";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") Long id,
                         HttpSession session){
        service.deleteType(id);
        session.setAttribute("operation","delete");
        return "redirect:/admin/types/1";
    }


    //跳转到修改分类标签的页面
    @GetMapping("/input/{id}")
    public String input(@PathVariable("id") Long id,Model model){
        Type type = service.getType(id);
        model.addAttribute("type",type);
        return "admin/types-input";
    }

    @GetMapping("/input")
    public String toAddInput(Model model,HttpSession session){
        if(session.getAttribute("msg") != null){
            model.addAttribute("msg","请勿重复添加标签!");
            session.removeAttribute("msg");
        }
        model.addAttribute("type",new Type());
        return "admin/types-input";
    }

    @PostMapping("/addType")
    public String addInput(@RequestParam("name") String name,
                           HttpSession session){
        Type typeByName = service.getTypeByName(name);
        if(typeByName != null){
            session.setAttribute("msg","");
            return "redirect:/admin/types/input";
        }
        service.saveType(name);
        session.setAttribute("operation","delete");
        return "redirect:/admin/types/1";
    }
}
