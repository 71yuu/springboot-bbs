package com.bbs.domain;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`admin`")
public class Admin {
    /**
     * 管理员ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 登录账号
     */
    @Column(name = "username")
    private String username;

    /**
     * 登录密码
     */
    @Column(name = "`password`")
    private String password;
}