package com.bbs.dto;

import com.bbs.domain.Comment;
import com.bbs.domain.Question;
import com.bbs.domain.User;
import lombok.Data;

/**
 * 评论 VO
 */
@Data
public class CommentVO extends Comment {
    /**
     * 用户
     */
    private UserVO user;

    /**
     * 是否点赞
     */
    private boolean liked;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 评论数量
     */
    private Integer commentCount;

    /**
     * 问题
     */
    private Question question;
}
