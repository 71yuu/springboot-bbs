package com.bbs.service;

import com.bbs.domain.User;
import com.bbs.dto.PageInfo;

public interface UserService{

    /**
     * 根据用户名获取用户
     *
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 用户注册
     *
     * @param username
     * @param password
     */
    void register(String username, String password);

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    User login(String username, String password);

    /**
     * 修改个人信息
     *
     * @param user
     */
    void update(User user);

    /***
     * 后台用户分页列表
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<User> listPage(int start, int length);

    /**
     * 管理员删除用户
     *
     * @param id
     */
    void deleteById(Integer id);

    /**
     * 根据 ID 查询用户信息
     *
     * @param id
     * @return
     */
    User getById(Integer id);

    /**
     * 更新用户等级
     *
     * @param id
     */
    void updateGrade(Integer id);

    /**
     * 增加用户级分
     *
     * @param id
     */
    void addIntegral(String integralType, Integer id);

    /**
     * 增加积分
     *
     * @param id
     * @param signIntegral
     */
    void integral(Integer id, Integer signIntegral);
}
