package com.wang.blog.service;

import com.wang.blog.bean.Comment;

import java.util.List;

/**
 * @author wangsiyuan
 */
public interface ICommentService {

    /**
     * 查找该博客的所有评论
     * @param id 博客的Id
     * @return 返回所有的评论
     */
    public List<Comment> listCommentByBlogId(Long id);

    /**
     *  新增博客
     * @param comment 评论
     */
    public void saveComment(Comment comment);

    /**
     *  删除单条评论
     * @param commentId 需要删除评论的Id
     * @param blogId 这个评论所属的博客Id
     */
    public void deleteComment(Long commentId,Long blogId);
}
