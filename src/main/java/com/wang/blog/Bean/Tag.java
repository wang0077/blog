package com.wang.blog.Bean;

import java.io.Serializable;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Tag implements Serializable {
    private Long tag_id;
    private String tag_name;
    private int count;

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
