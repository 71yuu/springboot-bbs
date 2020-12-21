package com.bbs.controller.admin;

import com.bbs.domain.Feedback;
import com.bbs.dto.FeedbackVO;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;
import com.bbs.service.FeedbackService;
import com.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 投诉反馈管理
 */
@Controller
@RequestMapping("admin/feedback")
public class AdminFeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 待反馈列表
     *
     * @return
     */
    @GetMapping("toFeedbackList")
    public String toFeedbackList  () {
        return "admin/feedback/toFeedbackList";
    }

    /**
     * 反馈列表
     *
     * @return
     */
    @GetMapping("toList")
    public String toList() {
       return "admin/feedback/list";
    }

    /**
     * 待反馈列表
     *
     * @return
     */
    @GetMapping("feedbackList")
    @ResponseBody
    public PageInfo<FeedbackVO> feedbackList(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<FeedbackVO> pageInfo = feedbackService.feedbackListPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 反馈列表
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<FeedbackVO>  list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<FeedbackVO> pageInfo = feedbackService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 回复反馈
     *
     * @param id
     * @return
     */
    @PostMapping("reply")
    public String feedback(Integer id, String reply) {
        feedbackService.update(id, reply);
        return "redirect:/admin/feedback/toFeedbackList";
    }

}
