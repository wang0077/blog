package com.wang.blog.Service.Impl;

import com.wang.blog.Bean.Comment;
import com.wang.blog.Dao.ICommentDao;
import com.wang.blog.Service.ICommentService;
import com.wang.blog.Service.admin.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements ICommentService {

    private final String HeadImage = "/images/headImage.png";

    private final String DeleteComment = "/*-----该评论已被注释-----*/";

    @Autowired
    private ICommentDao commentDao;

    @Override
    public List<Comment> ListCommentByBlogId(Long id) {
        List<Comment> comments = commentDao.ListCommentByBlogId(id);
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments){
            if(comment.getPatentId() == null){
                commentList.add(comment);
                //获取父节点回空的comment进行深搜
                DFSByComment(comments,comment,comment);
            }
        }
        return commentList;
    }

    //通过深度优先搜素获取相关评论，把各个级别的回复评论并在根级下的列表
    public void DFSByComment(List<Comment> commentsAll, Comment RootComment,Comment ParentComment){
        for (Comment ChildComment : commentsAll){
            if(ChildComment.getPatentId() != null){
                if(ChildComment.getPatentId().equals(ParentComment.getId())){
                    RootComment.getReplyComment().add(ChildComment);
                    //这里子父节点相互引用，一定不要调用ToString方法，不然会死循环溢出！！！！！！！！
                    ChildComment.setParentComment(ParentComment);
                    DFSByComment(commentsAll,RootComment,ChildComment);
                }
            }
        }
    }

    public void saveComment(Comment comment) {
        if(comment.getAvatar() == null){
            comment.setAvatar(HeadImage);
        }
        comment.setCreateTime(new Date());
        if(comment.getPatentId() == -1){
            comment.setPatentId(null);
        }
        commentDao.saveComment(comment);
    }

    @Override
    @Transactional
    public void deleteComment(Long commentId, Long BlogId) {
        commentDao.DeleteComment(commentId,BlogId,DeleteComment);
    }
}
