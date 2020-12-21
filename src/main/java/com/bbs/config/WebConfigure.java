package com.bbs.config;

import com.bbs.interceptor.AdminLoginInterceptor;
import com.bbs.interceptor.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

/**
 * 拦截器配置
 */
@Configuration
public class WebConfigure implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 管理员登录拦截
        InterceptorRegistration adminRegistration = registry.addInterceptor(new AdminLoginInterceptor());
        // 拦截除了登录页面的所有页面
        adminRegistration.addPathPatterns("/admin/**");
        // 排除登录页面
        adminRegistration.excludePathPatterns("/admin/login");

        // 前台系统登录拦截
        InterceptorRegistration loginRegistration = registry.addInterceptor(new LoginInterceptor());
        // 拦截系统需要拦截的页面
        loginRegistration.addPathPatterns("/user/**", "/question/publish", "/like", "/feedback/*", "/sign/**");
    }


}
