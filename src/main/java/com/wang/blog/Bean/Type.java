package com.wang.blog.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wangsiyuan
 */
public class Type implements Serializable {
    /**
     * 分类Id
     */
    private Long id;
    /**
     * 分类的名字
     */
    private String name;
    /**
     * 分类的数量
     */
    private int count;
    /**
     * 分类对应的博客,一对多
     */
    private List<Blog> blogList = new ArrayList<>();

    public Type() {
    }

    public List<Blog> getBlogList() {
        return blogList;
    }

    public void setBlogList(List<Blog> blogList) {
        this.blogList = blogList;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", count=" + count +
                ", blogList=" + blogList +
                '}';
    }
}
