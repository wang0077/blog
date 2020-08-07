package com.wang.blog.Dao.admin;

import com.wang.blog.Bean.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Mapper
@Repository
public interface IUserDao {
    public User findByUser(@Param("username") String Username,@Param("password") String password);
}
