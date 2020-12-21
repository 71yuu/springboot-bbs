package com.bbs.enums;

/**
 * 通用常量
 */
public class CommonConstant {

    /**
     * 问题状态(0: 待审核，1：审核通过，2：审核失败）
     */
    public static final Integer QUESTION_TO_AUDIT = 0;
    public static final Integer QUESTION_YES_AUDIT = 1;
    public static final Integer QUESTION_NO_AUDIT = 2;

    /**
     * 点赞类型（0：问题，1：评论）
     */
    public static final Integer LIKE_QUESTION = 0;
    public static final Integer LIKE_COMMENT = 1;

    /**
     * 通知类型（0：点赞问题，1：评论问题，2：点赞评论，3：回复评论）
     */
    public static final Integer NOTIFICATION_QUESTION_LIKE = 0;
    public static final Integer NOTIFICATION_QUESTION_COMMENT = 1;
    public static final Integer NOTIFICATION_COMMENT_LIKE = 2;
    public static final Integer NOTIFICATION_COMMENT_COMMENT = 3;
    public static final Integer NOTIFICATION_QUESTION_AUDIT = 4;
    public static final Integer NOTIFICATION_QUESTION_AUDIT_FAIL = 6;
    public static final Integer NOTIFICATION_FEEDBACK = 5;

    /**
     * 删除标记（0：正常，1：已删除）
     */
    public static final Integer DEL_FLAG_NORMAL = 0;
    public static final Integer DEL_FLAG_DEL = 1;

    /**
     * 投诉反馈（0：待投诉，1：待反馈）
     */
    public static final Integer FEEDBACK_TO = 0;
    public static final Integer FEEDBACK_ON = 1;

}
