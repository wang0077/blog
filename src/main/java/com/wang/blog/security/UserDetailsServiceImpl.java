package com.wang.blog.security;

import com.wang.blog.bean.User;
import com.wang.blog.dao.admin.IUserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * @author wangsiyuan
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private IUserDao userDao;

    @Autowired
    public void setUserDao(IUserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.loadUserByName(username);
        if(user == null){
            user = new User();
        }
        return user;
    }
}
