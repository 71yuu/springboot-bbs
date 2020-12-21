package com.bbs.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

@Data
@Table(name = "sign")
public class Sign {
    /**
     * 签到ID
     */
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户ID
     */
    @Column(name = "sign_user")
    private Integer signUser;

    /**
     * 签到时间
     */
    @Column(name = "sign_time")
    private Date signTime;

    /**
     * 签到奖励积分
     */
    @Column(name = "sign_integral")
    private Integer signIntegral;

    /**
     * 连续签到天数
     */
    @Column(name = "continuity_sign")
    private Integer continuitySign;
}
