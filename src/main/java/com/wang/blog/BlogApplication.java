package com.wang.blog;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author wangsiyuan
 */
@MapperScan(value = "com.wang.blog.dao")
@SpringBootApplication
//@ComponentScan(value = "com.wang.blog")
public class BlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
