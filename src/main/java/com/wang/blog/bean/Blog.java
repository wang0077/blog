package com.wang.blog.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * @author wangsiyuan
 */
public class Blog implements Serializable {
    /**
     * 主键
     */
    private Long id;
    /**
     * 文章标题
     */
    private  String title;
    /**
     * 文章内容
     */
    private String content;
    /**
     * 文章首图地址
     */
    private String firstPicture;
    /**
     * 标记转载原创的信息
     */
    private String flag;
    /**
     * 浏览次数
     */
    private Integer views;
    /**
     * 文章描述，放在首页小框框里面
     */
    private String description;
    /**
     * 赞赏是否开启
     */
    private boolean appreciation;
    /**
     * 转载声明是否开启
     */
    private boolean shareStatement;
    /**
     * 评论是否开启
     */
    private boolean commentabled;
    /**
     * 是否公开，状态发布或者保存
     */
    private boolean published;
    /**
     * 是否推荐
     */
    private boolean recommend;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 分配type的外键
     */
    private Long type_id;
    /**
     * 用户User的外键
     */
    private Long User_id;
    /**
     * 标签Tag的外键
     */
    private String Tag_id;
    /**
     * 分类实体一对一
     */
    private Type type;
    /**
     * 标签实体一对多
     */
    private List<Tag> tagList = new ArrayList<>();
    /**
     * 用户实体一对一
     */
    private User user;
    /**
     * 留言实体一对多
     */
    private List<Comment> commentList = new ArrayList<>();

    public Blog() {
    }


    public String getTag_id() {
        return Tag_id;
    }

    public void setTag_id(String tag_id) {
        Tag_id = tag_id;
    }

    public Long getType_id() {
        return type_id;
    }

    public void setType_id(Long type_id) {
        this.type_id = type_id;
    }

    public Long getUser_id() {
        return User_id;
    }

    public void setUser_id(Long user_id) {
        User_id = user_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public List<Tag> getTagList() {
        return tagList;
    }

    public void setTagList(List<Tag> tagList) {
        this.tagList = tagList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public void setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public void setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public void setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
    }

    public boolean isCommentabled() {
        return commentabled;
    }

    public void setCommentabled(boolean commentabled) {
        this.commentabled = commentabled;
    }

    public boolean isPublished() {
        return published;
    }

    public void setPublished(boolean published) {
        this.published = published;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public void setRecommend(boolean recommend) {
        this.recommend = recommend;
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
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", description='" + description + '\'' +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", published=" + published +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", type_id=" + type_id +
                ", User_id=" + User_id +
                ", Tag_id=" + Tag_id +
                ", type=" + type +
                ", tagList=" + tagList +
                ", user=" + user +
                ", commentList=" + commentList +
                '}';
    }
}
