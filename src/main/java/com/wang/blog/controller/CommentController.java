package com.wang.blog.controller;

import com.wang.blog.bean.Comment;
import com.wang.blog.bean.User;
import com.wang.blog.service.ICommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author wangsiyuan
 */
@Controller
public class CommentController {


    ICommentService commentService;

    @Autowired
    public void setCommentService(ICommentService commentService) {
        this.commentService = commentService;
    }

    /**
     *  获取博客底下的评论
     *
     */
    @GetMapping("/comment/{blogId}")
    public String commentList(@PathVariable("blogId") Long id, Model model){
        model.addAttribute("Comment",commentService.listCommentByBlogId(id));
        return "blog::commentList";
    }

    /**
     * 新增评论
     *
     */
    @PostMapping("/comment")
    public String post(Comment comment, HttpSession session){
        User user = (User) session.getAttribute("user");
        if(user != null){
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
            comment.setAvatar(user.getAvatar());
        }
        commentService.saveComment(comment);
        return "redirect:/comment/" + comment.getBlogId();
    }

    /**
     *
     * 删除评论
     */
    @PostMapping("/Comment/delete")
    public String delete(@RequestParam("CommentId") Long commentId, @RequestParam("BlogId") Long blogId){
        commentService.deleteComment(commentId,blogId);
        return "redirect:/comment/" + blogId;
    }
}
