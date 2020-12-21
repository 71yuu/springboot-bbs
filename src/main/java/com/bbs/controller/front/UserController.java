package com.bbs.controller.front;

import com.bbs.domain.Grade;
import com.bbs.domain.User;
import com.bbs.dto.NotificationVO;
import com.bbs.dto.PageResult;
import com.bbs.dto.QuestionVO;
import com.bbs.dto.UserVO;
import com.bbs.service.GradeService;
import com.bbs.service.NotificationService;
import com.bbs.service.QuestionService;
import com.bbs.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;

/**
 * 用户 Controller
 */
@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private GradeService gradeService;

    /**
     * 跳转到个人信息页面
     *
     * @return
     */
    @GetMapping("userInfo")
    public String toUserInfo(HttpSession session) {
        // 查询用户信息
        User user = (User) session.getAttribute("user");
        user = userService.getById(user.getId());

        // 转换 VO
        // 查询用户等级
        Grade grade = gradeService.getById(user.getGradeId());
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setGrade(grade);

        // 计算百分比
        // 查询下一个等级
        Integer currGrade = grade.getGrade();
        ++currGrade;
        if (currGrade <= 8) {
            Grade nextGrade = gradeService.getByGrade(currGrade);
            // 创建一个数值格式化对象
            NumberFormat numberFormat = NumberFormat.getInstance();
            // 设置精确到小数点后2位
            numberFormat.setMaximumFractionDigits(2);
            String result = numberFormat.format((float)userVO.getIntegral()/(float)nextGrade.getScore()*100);
            userVO.setPercentage(result);
            userVO.setNextIntegral(nextGrade.getScore());
        } else {
            userVO.setPercentage("100");
            userVO.setNextIntegral(grade.getScore());
        }

        session.setAttribute("user", userVO);
        return "userInfo";
    }

    /**
     * 修改个人信息
     *
     * @return
     */
    @PostMapping("userInfo/update")
    public String updateUserInfo(User user, String rePassword, Model model, HttpSession session) {
        // 参数检验
        if (StringUtils.isEmpty(user.getId())) {
            model.addAttribute("error", "用户信息错误");
            return "userInfo";
        }
        if (!StringUtils.isEmpty(rePassword) && !rePassword.equals(user.getPassword())) {
            model.addAttribute("error", "两次密码输入不一致");
            return "userInfo";
        }

        // 为空处理
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(null);
        }
        if (StringUtils.isEmpty(user.getNickname())) {
            user.setNickname(null);
        }

        userService.update(user);
        // 重新设置 session 中的用户信息
        session.setAttribute("user", user);
        model.addAttribute("message", "修改成功");
        model.addAttribute("redirect", "/");
        return "message";
    }

    /**
     * 我的页面
     *
     * @return
     */
    @GetMapping("{action}")
    public String myQuestions(@PathVariable(name = "action") String action,
                                Model model,
                                HttpSession session,
                                @RequestParam(name = "page", defaultValue = "1") Integer page,
                                @RequestParam(name = "size", defaultValue = "10") Integer size) {
        User user = (User) session.getAttribute("user");
        switch (action) {
            case "update":
                return "update_profile";
            case "questions":
                model.addAttribute("section", "questions");
                model.addAttribute("sectionName", "我的提问");
                PageResult<QuestionVO> questionPage = questionService.listByUser(user.getId(), page, size);
                model.addAttribute("pagination", questionPage);
                break;
            case "replies":
                model.addAttribute("section", "replies");
                model.addAttribute("sectionName", "最新回复");
                PageResult<NotificationVO> notificationPagination = notificationService.listByCommentUser(user.getId(), page, size);
                model.addAttribute("pagination", notificationPagination);
                break;
            case "likes":
                model.addAttribute("section", "likes");
                model.addAttribute("sectionName", "收到的赞");
                PageResult<NotificationVO> likePagination = notificationService.listByLikeUser(user.getId(), page, size);
                model.addAttribute("pagination", likePagination);
                break;
            case "audits":
                model.addAttribute("section", "audits");
                model.addAttribute("sectionName", "文章审核");
                PageResult<NotificationVO> auditPagination = notificationService.listByAuditUser(user.getId(), page, size);
                model.addAttribute("pagination", auditPagination);
                break;
            case "feedbacks":
                model.addAttribute("section", "feedbacks");
                model.addAttribute("sectionName", "投诉反馈回复");
                PageResult<NotificationVO> feedbackPagination = notificationService.listByFeedbackUser(user.getId(), page, size);
                model.addAttribute("pagination", feedbackPagination);
                break;
            default:
                model.addAttribute("section", "null");
                model.addAttribute("sectionName", "null");
                model.addAttribute("pagination", null);
                break;
        }
        return "notification";
    }
}
