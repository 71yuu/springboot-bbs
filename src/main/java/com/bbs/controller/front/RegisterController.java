package com.bbs.controller.front;

import com.bbs.domain.User;
import com.bbs.enums.CommonConstant;
import com.bbs.enums.LoginMessage;
import com.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 注册 Controller
 */
@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    /**
     * 跳转到 注册 页面
     * @return
     */
    @GetMapping("register")
    public String toRegister() {
        return "register";
    }

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param password 密码
     * @param rePassword 重复密码
     * @return
     */
    @PostMapping("register")
    public String register(@RequestParam String username,
                           @RequestParam String password,
                           @RequestParam String rePassword, Model model) {
        // 校验参数
        if (StringUtils.isEmpty(username)) {
            model.addAttribute("error", LoginMessage.USERNAME_EMPTY);
            return "register";
        }
        if (StringUtils.isEmpty(password)) {
            model.addAttribute("error", LoginMessage.PASSWORD_EMPTY);
            return "register";
        }
        if (StringUtils.isEmpty(rePassword)) {
            model.addAttribute("error", LoginMessage.RE_PASSWORD_EMPTY);
            return "register";
        }
        if (!password.equals(rePassword)) {
            model.addAttribute("error", LoginMessage.PASSWORD_NOT_MATCH);
            return "register";
        }

        // 判断用户是否已经注册
        User user = userService.getByUsername(username);
        if (user != null) {
            model.addAttribute("error", LoginMessage.USERNAME_EXISTS);
            return "register";
        }

        // 用户注册
        userService.register(username, password);
        model.addAttribute("message", "注册成功，请登录");
        model.addAttribute("redirect", "/login");
        return "message";
    }
}
