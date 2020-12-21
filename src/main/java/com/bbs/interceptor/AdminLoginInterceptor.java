package com.bbs.interceptor;

import com.bbs.domain.Admin;
import com.bbs.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 后台管理系统登录拦截
 */
public class AdminLoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 从 session 中获取用户登录
        Admin admin = (Admin) session.getAttribute("admin");
        // 未登录
        if (admin == null) {
            // ajax 请求返回 401
            if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
                response.sendError(401);
                return false;
            }

            // 跳转到登录页面
            response.sendRedirect("/admin/login");
            return false;
        }
        return true;
    }
}
