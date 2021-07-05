package com.wang.blog.bean;

/**
 * 博客和标签的中间表
 * @author wangsiyuan
 */
public class Blog_Tag {
    /**
     *  博客Id
     */
    private Long blogs;
    /**
     * 标签Id
     */
    private Long tags;

    public Long getBlogs() {
        return blogs;
    }

    public void setBlogs(Long blogs) {
        this.blogs = blogs;
    }

    public Long getTags() {
        return tags;
    }

    public void setTags(Long tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Blog_Tag{" +
                "blogs=" + blogs +
                ", tags=" + tags +
                '}';
    }
}
