package com.wang.blog.Controller.admin;

import com.wang.blog.Bean.User;

import com.wang.blog.Service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    IUserService userService;

    @GetMapping
    public String loginPage(){
        return "admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password,
                        HttpSession session,
                        RedirectAttributes attributes){
        //MD5加密，确保密码传输安全
        System.out.println(userService + "ser");
        System.out.println(DigestUtils.md5DigestAsHex(password.getBytes()));

        User user = userService.checkUser(username,DigestUtils.md5DigestAsHex(password.getBytes()));
        System.out.println(DigestUtils.md5DigestAsHex(password.getBytes()) + "!!!!!!------密码");
        if(user == null){
            attributes.addFlashAttribute("message","用户名或密码错误!");
            return "redirect:/admin";
        }else {
            user.setPassword(null);
            session.setAttribute("user",user);
            return "admin/index";
        }
    }

    @GetMapping("/loginOut")
    public String loginOut(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }
}
