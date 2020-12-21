package com.bbs.interceptor;

import com.bbs.domain.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @ClassName: LoginInceptor
 * @Auther: Yuu
 * @Date: 2020/11/22 17:46
 * @Description:
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        // 从 session 中获取用户登录
        User user = (User) session.getAttribute("user");
        // 未登录
        if (user == null) {
            // ajax 请求返回 401
            if ("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
                response.sendError(401);
                return false;
            }

            // 跳转到登录页面
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
}
