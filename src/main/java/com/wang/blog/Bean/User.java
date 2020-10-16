package com.wang.blog.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangsiyuan
 */
public class User implements Serializable {
    /**
     * 用户的Id,主键
     */
    private Long id;
    /**
     * 昵称,回显页面用的
     */
    private String nickname;
    /**
     * 用户名,登录用的
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 用户的分类
     */
    private Integer type;
    /**
     * 用户创建时间
     */
    private Date createTime;
    /**
     * 更新时间(好像没啥用)
     */
    private Date updateTime;
    /**
     * 用户底下对应的博客,一对多,(貌似个人博客用不着)
     */
    private List<Blog> blogList = new ArrayList<>();

    public User() {
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", avatar='" + avatar + '\'' +
                ", type=" + type +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}
