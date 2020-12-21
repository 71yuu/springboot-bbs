package com.bbs.dto;

import com.bbs.domain.Grade;
import com.bbs.domain.User;
import lombok.Data;

import java.io.Serializable;

@Data
public class UserVO extends User implements Serializable {

    /**
     * 用户等级
     */
    private Grade grade;

    /**
     * 积分百分比
     */
    private String percentage;

    /**
     * 下一次的积分
     */
    private Integer nextIntegral;
}
