package com.wang.blog.Service.admin.impl;

import com.wang.blog.Bean.User;
import com.wang.blog.Dao.admin.IUserDao;
import com.wang.blog.Service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserDao userDao;

    public void test(){
        System.out.println(userDao);

    }

    @Override
    public User checkUser(String username, String password) {
        System.out.println(userDao + "dao");
        return userDao.findByUser(username,password);
    }
}
