package com.bbs.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`comment`")
public class Comment {
    /**
     * 评论ID
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 问题ID
     */
    @Column(name = "question_id")
    private Integer questionId;

    /**
     * 父评论ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 评论内容
     */
    @Column(name = "content")
    private String content;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 删除标记（0：正常，1：删除）
     */
    @Column(name = "del_flag")
    private Integer delFlag = 0;
}
