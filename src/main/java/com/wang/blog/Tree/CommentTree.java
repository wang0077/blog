package com.wang.blog.Tree;

import com.wang.blog.Bean.Comment;

import java.util.ArrayList;
import java.util.List;

public class CommentTree {
    private Comment comment;
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
