package com.wang.blog.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangsiyuan
 */
public class Tag implements Serializable {
    /**
     * 标签Id
     */
    private Long tag_id;
    /**
     * 标签名字
     */
    private String tag_name;
    /**
     * 标签数量
     */
    private int count;
    /**
     * 标签对应的博客,一对多
     */
    private List<Blog> blogList = new ArrayList<>();

    public Tag() {

    }
    public Long getTag_id() {
        return tag_id;
    }

    public void setTag_id(Long tag_id) {
        this.tag_id = tag_id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTag_name() {
        return tag_name;
    }

    public void setTag_name(String tag_name) {
        this.tag_name = tag_name;
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "tag_id=" + tag_id +
                ", tag_name='" + tag_name + '\'' +
                ", blogList=" + blogList +
                '}';
    }
}
