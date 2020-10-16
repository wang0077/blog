package com.wang.blog.dao;

import com.wang.blog.bean.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author wangsiyuan
 */
//@Mapper
@Repository
public interface ICommentDao {

    /**
     *  对应博客下的所有评论。获取以后再进行处理
     * @param id 博客的ID
     * @return 对应博客下的所有评论。
     */
    List<Comment> listCommentByBlogId(@Param("id") Long id);

    /**
     * 需要存入的评论内容，包括Blog的ID和ParentId
     * @param comment 评论的内容
     */
    void saveComment(@Param("comment") Comment comment);

    /**
     *  命名为删除功能，实际上是使用 *-----该评论已被注释-----* 覆盖原来的评论
     * @param commentId 当前博客下的评论下的Id值
     * @param blogId 需要替换评论的博客Id
     * @param str 替换的内容 *-----该评论已被注释-----*
     *
     */
    void deleteComment(@Param("cid") Long commentId, @Param("bid") Long blogId, @Param("content") String str);

    /**
     * 删除博客时删除底下所有的评论
     * @see com.wang.blog.dao.ICommentDao#deleteComment(Long, Long, String)
     *      这个方法是只删除博客底下的一条评论，与这个方法不同，这个方法是删除博客并删除所有评论
     * @param id Blog的Id
     */
    void deleteCommentById(@Param("id") Long id);
}
