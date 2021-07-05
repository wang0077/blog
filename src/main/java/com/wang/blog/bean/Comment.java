package com.wang.blog.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangsiyuan
 */
public class Comment implements Serializable {
    /**
     * 评论的Id,主键
     */
    private Long id;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 评论内容
     */
    private String content;
    /**
     * 评论的头像
     */
    private String avatar;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 父节点的Id
     */
    private Long parentId;
    /**
     * 归属博客的Id
     */
    private Long blogId;
    /**
     * 是否是管理员的评论
     */
    private boolean adminComment;
    /**
     * 博客的实体
     */
    private Blog blog;
    /**
     * 子节点的评论
     */
    private List<Comment> replyComment = new ArrayList<>();
    /**
     * 父节点的评论实体
     */
    private Comment parentComment;

    public Comment() {

    }

    public Comment getParentComment() {
        return parentComment;
    }

    public void setParentComment(Comment parentComment) {
        this.parentComment = parentComment;
    }

    public List<Comment> getReplyComment() {
        return replyComment;
    }

    public void setReplyComment(List<Comment> replyComment) {
        this.replyComment = replyComment;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Blog getBlog() {
        return blog;
    }

    public void setBlog(Blog blog) {
        this.blog = blog;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isAdminComment() {
        return adminComment;
    }

    public void setAdminComment(boolean adminComment) {
        this.adminComment = adminComment;
    }

    public Long getBlogId() {
        return blogId;
    }

    public void setBlogId(Long blogId) {
        this.blogId = blogId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                ", email='" + email + '\'' +
                ", content='" + content + '\'' +
                ", avatar='" + avatar + '\'' +
                ", createTime=" + createTime +
                ", parentId=" + parentId +
                ", blogId=" + blogId +
                ", blog=" + blog +
                ", replyComment=" + replyComment +
                ", parentComment=" + parentComment +
                '}';
    }
}
