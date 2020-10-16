package com.wang.blog.config;


import com.wang.blog.component.IndexInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author wangsiyuan
 */
@Configuration
public class MyMvcConfig implements WebMvcConfigurer {
    @Override

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/index").setViewName("/index/1");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new IndexInterceptor())
                .addPathPatterns("/");
    }
}
