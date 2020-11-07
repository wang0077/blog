package com.wang.blog.cache.redis;

import com.wang.blog.bean.Blog;

import java.util.List;

/**
 * @author wangsiyuan
 */
public interface ITagBlogByRedis {

    /**
     * 将博客和标签的Id保存在中间表
     * @param blogId 博客的Id
     * @param tagId 标签的Id
     */
    void saveBlogTag(Long blogId,Long tagId);

    /**
     * 在中间表的标签字段加入新的博客内容
     * @param blogId 博客的Id
     * @param tagId 标签的Id
     */
    void updateBlog(Long blogId,Long tagId);

    /**
     * 删除中间表的标签字段
     * @param blogId 博客的id
     * @param tagId 标签的Id
     */
    void delBlogTag(Long blogId,Long tagId);

    /**
     *  删除整个字段
     * @param blogId 博客Id
     */
    void delBlogTag(Long blogId);

    /**
     * 通过博客的Id得到该博客的标签
     * @param blogId 博客Id
     * @return 返回博客列表
     */
    List<Integer> listTagByBlogId(Long blogId);

}
