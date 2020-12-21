package com.bbs.controller.front;

import com.bbs.domain.Grade;
import com.bbs.domain.User;
import com.bbs.dto.UserVO;
import com.bbs.enums.CommonConstant;
import com.bbs.enums.LoginMessage;
import com.bbs.service.GradeService;
import com.bbs.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.text.NumberFormat;

/**
 * 登录 Controller
 */
@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @Autowired
    private GradeService gradeService;

    /**
     * 跳转到登录页面
     *
     * @return
     */
    @GetMapping("login")
    public String toLogin() {
       return "login";
    }

    /**
     * 登录功能
     *
     * @return
     */
    @PostMapping("login")
    public String login(@RequestParam String username,
                        @RequestParam String password, Model model, HttpSession session) {
        // 校验参数
        if (StringUtils.isEmpty(username)) {
            model.addAttribute("error", LoginMessage.USERNAME_EMPTY);
            return "login";
        }
        if (StringUtils.isEmpty(password)) {
            model.addAttribute("error", LoginMessage.PASSWORD_EMPTY);
            return "login";
        }

        // 登录
        User user = userService.login(username, password);
        // 登录失败
        if (user == null) {
            model.addAttribute("error", LoginMessage.WRONG_PASSWORD);
            return "login";
        }

        // 账号是否被管理员删除
        else if (user.getDelFlag().equals(CommonConstant.DEL_FLAG_DEL)) {
            model.addAttribute("error", LoginMessage.WRONG_DELETE);
            return "login";
        }

        // 登录成功
        else {
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

            // 保持登录状态，存入 session
            session.setAttribute("user", userVO);
            return "redirect:/";
        }
    }

    /**
     * 退出登录
     *
     * @return
     */
    @GetMapping("logout")
    public String logout(HttpSession session) {
        session.setAttribute("user", null);
        return "redirect:/";
    }
}
