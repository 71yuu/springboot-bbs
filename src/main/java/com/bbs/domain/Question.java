package com.bbs.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "question")
public class Question {
    /**
     * 问题 ID
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 问题标题
     */
    @Column(name = "title")
    private String title;

    /**
     * 问题内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 问题文本
     */
    @Column(name = "text")
    private String text;

    /**
     * 是否审核（0：待审核，1：审核通过，2：审核失败）
     */
    @Column(name = "state")
    private Integer state;

    /**
     * 发布者ID
     */
    @Column(name = "creator")
    private Integer creator;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除标记（0：正常，1：已删除）
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;

    /**
     * 浏览数
     */
    @Column(name = "view_count")
    private Integer viewCount;

    /**
     * 分类ID
     */
    @Column(name = "cat_id")
    private Integer catId;
}
