package com.wang.blog.Service;

import com.wang.blog.Bean.Comment;

import java.util.List;

public interface ICommentService {

    public List<Comment> ListCommentByBlogId(Long id);

    public void saveComment(Comment comment);

    public void deleteComment(Long commentId,Long BlogId);
}
