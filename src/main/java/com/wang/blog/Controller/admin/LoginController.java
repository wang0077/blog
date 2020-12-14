package com.wang.blog.controller.admin;


import com.wang.blog.bean.User;
import com.wang.blog.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * @author wangsiyuan
 */
@Controller
@RequestMapping("/admin")
public class LoginController {

    IUserService userService;

    @Autowired
    public void setUserService(IUserService userService) {
        this.userService = userService;
    }

    /**
     *
     * 跳转登录页面
     */
    @GetMapping
    public String loginPage(){
        return "admin/login";
    }


    @PostMapping("/successLogin")
    public String successLogin(HttpServletRequest request){
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        HttpSession session = request.getSession();
        User user = (User) authentication.getPrincipal();
        user.setPassword(null);
        session.setAttribute("user",user);
        return "admin/index";
    }

    @PostMapping("/failLogin")
    public String failLogin(RedirectAttributes attributes){
        attributes.addFlashAttribute("message","用户名或密码错误!");
        return "redirect:/admin";
    }

    /**
     *
     * 登出功能
     */
    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
