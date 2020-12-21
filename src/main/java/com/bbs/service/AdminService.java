package com.bbs.service;

import com.bbs.domain.Admin;

public interface AdminService{


    /**
     * 登录
     *
     * @param admin
     * @return
     */
    Admin login(Admin admin);
}
