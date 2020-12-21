package com.bbs.dto;

import com.bbs.domain.Sign;
import com.bbs.domain.User;
import lombok.Data;

import java.io.Serializable;

/**
 * @ClassName: SignVO
 * @Auther: Yuu
 * @Date: 2020/12/20 19:46
 * @Description:
 */
@Data
public class SignVO extends Sign implements Serializable {

    /**
     * 签到用户
     */
    private User user;
}
