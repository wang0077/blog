package com.wang.blog.service;

import com.wang.blog.bean.Comment;

import java.util.List;

public interface ICommentService {

    public List<Comment> listCommentByBlogId(Long id);

    public void saveComment(Comment comment);

    public void deleteComment(Long commentId,Long BlogId);
}
