package com.bbs.dto;

import com.bbs.domain.Feedback;
import com.bbs.domain.User;
import lombok.Data;

/**
 * 反馈 VO
 */
@Data
public class FeedbackVO extends Feedback {

    /**
     * 用户
     */
    private User user;
}
