package com.bbs.controller.admin;

import com.bbs.domain.Admin;
import com.bbs.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

/**
 * 管理员 登录 Controller
 */
@Controller
@RequestMapping("admin")
public class AdminLoginController {

    @Autowired
    private AdminService adminService;

    /**
     * 跳转到管理员登录页面
     *
     * @return
     */
    @GetMapping("login")
    public String toAdminLogin() {
        return "admin/login";
    }

    /**
     * 后台系统登录
     *
     * @return
     */
    @PostMapping("login")
    public String adminLogin(Admin admin, HttpSession session, Model model) {
        Admin loginAdmin = adminService.login(admin);

        // 登录成功
        if (loginAdmin != null) {
            session.setAttribute("admin", loginAdmin);
            return "redirect:/admin/cat/toList";
        }

        // 登录失败
        else {
            model.addAttribute("message", "用户名或密码错误");
            return "admin/login";
        }
    }

    /**
     * 退出登录
     *
     * @param session
     * @return
     */
    @GetMapping("logout")
    public String adminLogout(HttpSession session) {
        session.removeAttribute("admin");
        return "redirect:/admin/login";
    }
}
