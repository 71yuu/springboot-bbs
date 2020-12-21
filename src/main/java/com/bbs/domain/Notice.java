package com.bbs.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "notice")
public class Notice {
    /**
     * 通知ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 通知内容
     */
    @Column(name = "notice")
    private String notice;

    /**
     * 通知时间
     */
    @Column(name = "notice_time")
    private Date noticeTime;

    /**
     * 删除标记
     */
    @Column(name = "del_flag")
    private Integer delFlag;
}
