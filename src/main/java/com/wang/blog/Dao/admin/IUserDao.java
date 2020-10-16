package com.wang.blog.dao.admin;

import com.wang.blog.bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author wangsiyuan
 */
@Mapper
@Repository
public interface IUserDao {
    /**
     * 登录匹配账号密码
     * @param username 用户名
     * @param password 密码
     * @return 返回得到User
     */
    public User findByUser(@Param("username") String username, @Param("password") String password);
}
