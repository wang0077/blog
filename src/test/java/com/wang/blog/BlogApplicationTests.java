package com.wang.blog;

import com.wang.blog.Bean.Blog;
import com.wang.blog.Bean.Comment;
import com.wang.blog.Bean.Page;
import com.wang.blog.Bean.Tag;
import com.wang.blog.Controller.admin.LoginController;
import com.wang.blog.Dao.ICommentDao;
import com.wang.blog.Dao.admin.IBlogDao;
import com.wang.blog.Dao.admin.ITagDao;
import com.wang.blog.Service.ICommentService;
import com.wang.blog.Service.admin.IBlogService;
import com.wang.blog.Service.admin.ITagService;
import com.wang.blog.Service.admin.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private IBlogService blogService;

    @Test
    public void test(){
//        LoginController loginController = new LoginController();
//        loginController.test();
        new UserServiceImpl().test();
    }
}
