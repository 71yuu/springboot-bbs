package com.bbs.dto;

import com.bbs.domain.Question;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 问题页面展示 VO
 */
@Data
public class QuestionVO extends Question implements Serializable {

    /**
     * 问题提问者
     */
    private UserVO user;

    /**
     * 评论数
     */
    private Integer commentCount;

    /**
     * 点赞数量
     */
    private Integer likeCount;

    /**
     * 是否点赞
     */
    private boolean liked = false;

    /**
     * 图片列表
     */
    private List<String> imgs;

    /**
     * 描述
     */
    private String desc;

}
