package com.bbs.controller.front;

import com.bbs.domain.Sign;
import com.bbs.domain.User;
import com.bbs.service.SignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @ClassName: SignController
 * @Auther: Yuu
 * @Date: 2020/12/15 16:43
 * @Description:
 */
@Controller
@RequestMapping("sign")
public class SignController {

    @Autowired
    private SignService signService;

    /**
     * 跳转到签到页面
     *
     * @return
     */
    @GetMapping("toSign")
    public String toSign() {
        return "sign";
    }

    /**
     * 判断今日是否签到
     * @param userId
     * @return
     */
    @GetMapping("todaySign")
    @ResponseBody
    public Sign todaySign(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Sign sign = signService.todaySign(user.getId());

        if (sign != null) {
            return sign;
        } else {
            return null;
        }
    }

    /**
     * 查询本月签到天数
     *
     * @param session
     * @return
     */
    @GetMapping("monthSign")
    @ResponseBody
    public List<Integer> monthSign(HttpSession session) {
        User user = (User) session.getAttribute("user");
        return signService.monthSign(user.getId());
    }

    /**
     * 签到
     *
     * @param session
     * @return
     */
    @PostMapping("sign")
    @ResponseBody
    public Sign sign(HttpSession session) {
        User user = (User) session.getAttribute("user");
        Sign sign = signService.sign(user.getId());
        return sign;
    }
}
