package com.bbs.controller.admin;

import com.bbs.constant.IntegralRuleConstant;
import com.bbs.domain.IntegralRule;
import com.bbs.domain.Notification;
import com.bbs.domain.Question;
import com.bbs.dto.NotificationVO;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;
import com.bbs.service.QuestionService;
import com.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * 问题管理
 */
@Controller
@RequestMapping("admin/question")
public class AdminQuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserService userService;

    /**
     * 待审核列表
     *
     * @return
     */
    @GetMapping("toAuditList")
    public String toAuditList() {
        return "admin/question/auditList";
    }

    /**
     * 问题列表
     *
     * @return
     */
    @GetMapping("toList")
    public String toList() {
       return "admin/question/list";
    }

    /**
     * 待审核问题列表
     *
     * @return
     */
    @GetMapping("auditList")
    @ResponseBody
    public PageInfo<QuestionVO>  auditList(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<QuestionVO> pageInfo = questionService.auditListPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 问题列表
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<QuestionVO>  list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<QuestionVO> pageInfo = questionService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 通过审核
     *
     * @param id
     * @return
     */
    @GetMapping("yesAudit")
    public String yesAudit(Integer id) {
       questionService.yesAudit(id);

       // 查询问卷的用户
        QuestionVO questionVO = questionService.getById(id);

        // 增加用户积分
       userService.addIntegral(IntegralRuleConstant.INTEGRAL_RULE_FBWT, questionVO.getUser().getId());

        return "redirect:/admin/question/toAuditList";
    }

    /**
     * 不通过审核
     *
     * @param id
     * @return
     */
    @GetMapping("noAudit")
    public String noAudit(String id) {
        questionService.noAudit(id);
        return "redirect:/admin/question/toAuditList";
    }

    /**
     * 删除问题
     *
     * @param id
     * @return
     */
    @GetMapping("delete")
    public String delete(Integer id) {
        questionService.deleteById(id);
        return "redirect:/admin/question/toList";
    }
}
