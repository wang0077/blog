package com.wang.blog.service.impl;

import com.wang.blog.bean.Comment;
import com.wang.blog.cache.redis.impl.CommentByRedis;
import com.wang.blog.dao.ICommentDao;
import com.wang.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author wangsiyuan
 */
@Service
public class CommentServiceImpl implements ICommentService {

    /**
     * 游客评论的头像
     */
    private final String HEAD_IMAGE = "/images/headImage.png";

    /**
     * 删除功能实际为替换，将要删除的评论用REPLACE_STR替换
     */
    private final String REPLACE_STR = "/*-----该评论已被注释-----*/";


    private ICommentDao commentDao;

    private CommentByRedis redis;

    @Autowired
    public void setRedis(CommentByRedis redis) {
        this.redis = redis;
    }

    @Autowired
    public void setCommentDao(ICommentDao commentDao) {
        this.commentDao = commentDao;
    }

    /**
     * 先用SQL语句查询出当前博客所有的评论(乱序状态)，通过DFS进行生成多叉树，通过遍历多叉树来获取排序完的评论
     * @param id 需要添加评论博客的Id
     * @return 返回排序完的评论
     */
    @Override
    public List<Comment> listCommentByBlogId(Long id) {
//        获取该博客下所有的评论
        List<Comment> comments = redis.listComment(id);
        System.out.println(comments.size() + "!!!!!!!!");
        if(comments == null || comments.size() == 0){
            comments = commentDao.listCommentByBlogId(id);
            if(comments != null && comments.size() != 0){
                redis.saveComment(comments,id);
            }
        }
        List<Comment> commentList = new ArrayList<>();
        for (Comment comment : comments){
//            如果该评论没有父节点，则认为是根节点
            if(comment.getParentId() == null){
//                将评论中的每个根节点加入List中，每一个Comment有List记录每一个子节点
                commentList.add(comment);
                //获取父节点回空的comment进行深搜
                dfsByComment(comments,comment,comment);
            }
        }
//        将每个已经将子节点填充入根节点的List返回
        return commentList;
    }


    /**
     * 通过深度优先搜素获取相关评论，把各个级别的回复评论并在根级下的列表
     * @param commentsAll 当前博客下的所有评论
     * @param rootComment 评论的根节点
     * @param parentComment 当前评论的父节点
     * @see Comment  if内的相互引用看Comment的定义
     *              对父节点是一对一
     *              对子节点是一对多 List
     */
    private void dfsByComment(List<Comment> commentsAll, Comment rootComment, Comment parentComment){
        for (Comment childComment : commentsAll){
            if(childComment.getParentId() != null){
//                子节点和父节点相互绑定，判断这个评论和传进去的父节点是否有关系
                if(childComment.getParentId().equals(parentComment.getId())){
//                    将遍历得到的子节点加入传入的父节点中
                    rootComment.getReplyComment().add(childComment);
                    //这里子父节点相互引用，一定不要调用ToString方法，不然会死循环溢出！！！！！！！！
//                    再把父节点加入子节点中
                    childComment.setParentComment(parentComment);
//                    继续递归搜索
                    dfsByComment(commentsAll,rootComment,childComment);
                }
            }
        }
    }

    /**
     * 插入新的评论
     * @param comment 需要保存的评论内容
     */
    @Override
    public void saveComment(Comment comment) {
//        如果评论内容未设置头像，则自动设置系统默认头像
        if(comment.getAvatar() == null){
            comment.setAvatar(HEAD_IMAGE);
        }
//        获取现在时间，设置评论时间
        comment.setCreateTime(new Date());
//        查看是否有父节点
        if(comment.getParentId() == -1){
            comment.setParentId(null);
        }
        commentDao.saveComment(comment);
        Long blogId = comment.getBlogId();
        redis.deleteComment(blogId);
        List<Comment> comments = commentDao.listCommentByBlogId(blogId);
        redis.saveComment(comments,blogId);
    }

    /**
     *
     * @param commentId 需要删除的评论Id
     * @param blogId 需要删除评论所属的Blog的Id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteComment(Long commentId, Long blogId) {
        commentDao.deleteComment(commentId,blogId,REPLACE_STR);
        redis.deleteComment(blogId);
        List<Comment> comments = commentDao.listCommentByBlogId(blogId);
        redis.saveComment(comments,blogId);
    }
}
