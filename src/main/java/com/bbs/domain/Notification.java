package com.bbs.domain;

import javax.persistence.*;
import lombok.Data;

import java.util.Date;

@Data
@Table(name = "notification")
public class Notification {
    /**
     * 通知ID
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 发布者
     */
    @Column(name = "notifer")
    private Integer notifer;

    /**
     * 接受者
     */
    @Column(name = "receiver")
    private Integer receiver;

    /**
     * 通知类型（0：点赞问题，1：点赞评论，2：评论文章，3：回复评论）
     */
    @Column(name = "`type`")
    private Integer type;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 业务ID（评论或者问题）
     */
    @Column(name = "service_id")
    private Integer serviceId;
}
