package com.wang.blog.Bean;

public class Blog_Tag {
    private Long blogs;
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
