package com.wang.blog.service.admin;

import com.wang.blog.bean.User;


/**
 * @author wangsiyuan
 */
public interface IUserService{
    /**
     * 登录匹配功能
     * @param username 用户名
     * @param password 密码
     * @return 查询到用户
     */
    public User checkUser(String username ,String password);
}
