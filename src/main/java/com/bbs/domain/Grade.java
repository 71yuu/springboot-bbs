package com.bbs.domain;

import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "grade")
public class Grade {
    /**
     * 等级ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 等级
     */
    @Column(name = "grade")
    private Integer grade;

    /**
     * 所需积分
     */
    @Column(name = "score")
    private Integer score;

    /**
     * 等级图标url
     */
    @Column(name = "icon_url")
    private String iconUrl;
}
