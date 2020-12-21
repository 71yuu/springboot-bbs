package com.bbs.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "`like`")
public class Like {
    /**
     * 点赞ID
     */
    @Id
    @Column(name = "id")
    private Integer id;

    /**
     * 点赞类型（0：问题，1：评论）
     */
    @Column(name = "like_type")
    private Integer likeType;

    /**
     * 点赞类型ID
     */
    @Column(name = "like_id")
    private Integer likeId;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;
}
