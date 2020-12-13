package com.wang.blog.cache.redis;

import com.wang.blog.bean.Comment;

import java.util.List;

/**
 * @author wangsiyuan
 */
public interface ICommentByRedis {
    /**
     * 保存评论
     * @param comments 当前博客下的所有评论
     * @param id 博客的Id
     */
    void saveComment(List<Comment> comments,Long id);

    /**
     * 获取当前博客的评论
     * @param id 博客Id
     * @return 返回当前博客的评论
     */
    List<Comment> listComment(Long id);

    /**
     * 删除当前博客缓存中的评论
     * @param id 博客的id
     */
    void deleteComment(Long id);
}
