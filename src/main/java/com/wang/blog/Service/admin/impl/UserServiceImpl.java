package com.wang.blog.service.admin.impl;

import com.wang.blog.bean.User;
import com.wang.blog.dao.admin.IUserDao;
import com.wang.blog.service.admin.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author wangsiyuan
 */
@Service
public class UserServiceImpl implements IUserService {

    private IUserDao userDao;

    @Autowired
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public User checkUser(String username, String password) {
        return userDao.findByUser(username,password);
    }
}
