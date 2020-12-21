package com.bbs.dto;

import com.bbs.domain.*;
import lombok.Data;

@Data
public class NotificationVO extends Notification {

    /**
     * 发送者
     */
    private User notiferUser;

    /**
     * 接受者
     */
    private User receiverUser;

    /**
     * 问题
     */
    private Question question;

    /**
     * 评论
     */
    private Comment comment;

    /**
     * 投诉反馈
     */
    private Feedback feedback;
}

