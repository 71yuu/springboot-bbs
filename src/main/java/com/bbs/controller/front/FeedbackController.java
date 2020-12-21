package com.bbs.controller.front;

import com.bbs.domain.Feedback;
import com.bbs.domain.User;
import com.bbs.enums.CommonConstant;
import com.bbs.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 投诉反馈 Controller
 */
@Controller
@RequestMapping("feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 提交投诉反馈
     *
     * @return
     */
    @PostMapping("submit")
    @ResponseBody
    public Integer submit(String content, HttpSession session) {
        // 获取当前登录人
        User user = (User)session.getAttribute("user");

        Feedback feedback = new Feedback();
        feedback.setUserId(user.getId());
        feedback.setContent(content);
        feedback.setCreateTime(new Date());
        feedback.setStatus(CommonConstant.FEEDBACK_TO);
        feedbackService.save(feedback);

        return 200;
    }
}
