package com.wang.blog.controller.admin;

import com.wang.blog.bean.User;

import com.wang.blog.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    /**
     *
     * 处理登录
     */
    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        //MD5加密，确保密码传输安全
        User user = userService.checkUser(username,DigestUtils.md5DigestAsHex(password.getBytes()));
        if(user == null){
            attributes.addFlashAttribute("message","用户名或密码错误!");
            return "redirect:/admin";
        }else {
            user.setPassword(null);
            session.setAttribute("user",user);
            session.setMaxInactiveInterval(0);
            return "admin/index";
        }
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
