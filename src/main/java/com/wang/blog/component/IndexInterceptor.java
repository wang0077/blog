package com.wang.blog.component;

import org.jetbrains.annotations.NotNull;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 映射首页
 * @author wangsiyuan
 */
public class IndexInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request,
                             HttpServletResponse response,
                             @NotNull Object handler) throws Exception {
        response.sendRedirect("index/1");
        return false;
    }
}
