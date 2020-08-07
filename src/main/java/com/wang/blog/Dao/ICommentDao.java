package com.wang.blog.Dao;

import com.wang.blog.Bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
//@Repository
public interface ICommentDao {

    public List<Comment> ListCommentByBlogId(@Param("id") Long id);

    public void saveComment(@Param("comment") Comment comment);

    Long DeleteComment(@Param("cid") Long commentId,@Param("bid") Long BlogId,@Param("content") String str);

    void DeleteCommentById(@Param("id") Long id);
}
