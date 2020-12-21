package com.bbs.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "feedback")
public class Feedback {
    /**
     * 反馈ID
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 反馈内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 是否处理（0：未处理，1：已处理）
     */
    @Column(name = "`status`")
    private Integer status;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 处理时间
     */
    @Column(name = "process_time")
    private Date processTime;

    /**
     * 管理员回复
     */
    @Column(name = "reply")
    private String reply;
}
