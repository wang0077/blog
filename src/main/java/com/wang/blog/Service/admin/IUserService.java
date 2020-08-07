package com.wang.blog.Service.admin;

import com.wang.blog.Bean.User;
import org.springframework.stereotype.Service;



public interface IUserService{
    public User checkUser(String username ,String password);
}
