package com.wb.controller;

import com.wb.pojo.Comment;
import com.wb.pojo.User;
import com.wb.service.BlogService;
import com.wb.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}") // 取到 yml文件里面的图片路径
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable("blogId") Integer blogId, Model model) {
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String postComment(Comment comment, HttpSession session) {
        /* 根据 blogId来查询 Blog对象，然后进行保存 */
        Integer blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));

        User user = (User) session.getAttribute("user");

        if (user != null && comment.getNickname().equals(user.getNickname()) && comment.getEmail().equals(user.getEmail())) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/"+ blogId;
    }
}
