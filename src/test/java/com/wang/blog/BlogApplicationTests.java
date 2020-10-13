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
import com.wang.blog.Service.ICountService;
import com.wang.blog.Service.admin.IBlogService;
import com.wang.blog.Service.admin.ITagService;
import com.wang.blog.Service.admin.impl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import redis.clients.jedis.Jedis;

import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@SpringBootTest
class BlogApplicationTests {

    @Autowired
    private ICountService service;

    @Test
    public void test(){
        System.out.println(service);
        System.out.println(service.getAccessCount());
//        Jedis jedis = new Jedis("localhost");
//        // 如果 Redis 服务设置来密码，需要下面这行，没有就不需要
//        // jedis.auth("123456");
////        jedis.set("Person","0");
//        jedis.incr("Person");
//        System.out.println(jedis.get("Person"));
//        System.out.println("连接成功");
//        //查看服务是否运行
//        System.out.println("服务正在运行: "+jedis.ping());
    }
}
