package com.bbs.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`user`")
public class User {
    /**
     * 用户 ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 用户名（登录账号）
     */
    @Column(name = "username")
    private String username;

    /**
     * 登录密码
     */
    @Column(name = "`password`")
    private String password;

    /**
     * 昵称
     */
    @Column(name = "nickname")
    private String nickname;

    /**
     * 头像地址
     */
    @Column(name = "head_url")
    private String headUrl;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除标记
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;

    /**
     * 等级积分
     */
    @Column(name = "integral")
    private Integer integral;

    /**
     * 当前等级ID
     */
    @Column(name = "grade_id")
    private Integer gradeId;
}
