package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.User;
import com.wang.blog.dao.admin.IUserDao;
import com.wang.blog.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    @Override
    public User checkUser(String username, String password) {
        System.out.println(userDao + "dao");
        return userDao.findByUser(username,password);
    }
}
