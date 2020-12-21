package com.bbs.controller.front;

import com.bbs.constant.IntegralRuleConstant;
import com.bbs.domain.Comment;
import com.bbs.domain.IntegralRule;
import com.bbs.domain.Notification;
import com.bbs.domain.User;
import com.bbs.dto.CommentVO;
import com.bbs.dto.QuestionVO;
import com.bbs.enums.CommonConstant;
import com.bbs.service.CommentService;
import com.bbs.service.NotificationService;
import com.bbs.service.QuestionService;
import com.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * 评论 Controller
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private UserService userService;

    /**
     * 发表评论
     *
     * @param comment
     * @return
     */
    @PostMapping("comment")
    @ResponseBody
    public Object comment(@RequestBody Comment comment, HttpSession session) {
        // 获取当前登录的用户
        User user = (User)session.getAttribute("user");
        comment.setUserId(user.getId());
        // 创建通知
        Notification notification = new Notification();
        // 发送人
        notification.setNotifer(comment.getUserId());
        notification.setCreateTime(new Date());
        if (comment.getParentId() == null) {
            comment.setParentId(0);

            // 接收人
            QuestionVO questionVO = questionService.getById(comment.getQuestionId());
            notification.setReceiver(questionVO.getCreator());

            // 设置类型
            notification.setType(CommonConstant.NOTIFICATION_QUESTION_COMMENT);

            // 业务ID
            notification.setServiceId(questionVO.getId());

            // 排除自己给自己评论
            if (!notification.getNotifer().equals(notification.getReceiver())) {
                // 收到评论
                userService.addIntegral(IntegralRuleConstant.INTEGRAL_RULE_WTSDPL, notification.getReceiver());
                // 发表评论
                userService.addIntegral(IntegralRuleConstant.INTEGRAL_RULE_FBPL, notification.getNotifer());
            }
        }

        else {
            // 接收人
            Comment reComment = commentService.getById(comment.getParentId());
            notification.setReceiver(reComment.getUserId());

            // 设置类型
            notification.setType(CommonConstant.NOTIFICATION_COMMENT_COMMENT);

            // 业务ID
            notification.setServiceId(reComment.getId());
        }

        notificationService.save(notification);
        commentService.save(comment);

        return 200;
    }

    /**
     * 获取评论的子评论
     *
     * @param commentId
     * @return
     */
    @GetMapping("comment/{commentId}")
    @ResponseBody
    public List<CommentVO> getByCommentId(@PathVariable Integer commentId) {
        List<CommentVO> commentVOS = commentService.getByCommentId(commentId);
        return commentVOS;
    }

}


