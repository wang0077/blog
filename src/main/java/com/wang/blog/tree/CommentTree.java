package com.wang.blog.tree;

import com.wang.blog.bean.Comment;

import java.util.ArrayList;
import java.util.List;

/**
 * 搭建评论多叉树的节点构造
 * @author wangsiyuan
 */
public class CommentTree {
    /**
     * 评论实体
     */
    private Comment comment;
    /**
     * 评论的子节点,一对多
     */
    private List<CommentTree> commentList = new ArrayList<>();

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    public List<CommentTree> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<CommentTree> commentList) {
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "CommentTree{" +
                "comment=" + comment +
                ", commentList=" + commentList +
                '}';
    }
}
