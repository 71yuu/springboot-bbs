package com.bbs.service.impl;

import com.bbs.domain.Admin;
import com.bbs.service.AdminService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bbs.mapper.AdminMapper;
import tk.mybatis.mapper.entity.Example;

@Service
public class AdminServiceImpl implements AdminService {

    @Resource
    private AdminMapper adminMapper;

    @Override
    public Admin login(Admin admin) {
        Example example = new Example(Admin.class);
        example.createCriteria().andEqualTo("username", admin.getUsername());
        Admin loginAdmin = adminMapper.selectOneByExample(example);
        // 匹配密码
        if (loginAdmin != null && loginAdmin.getPassword().equals(admin.getPassword())) {
            return loginAdmin;
        }
        return null;
    }
}
