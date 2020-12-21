package com.bbs.service;

import com.bbs.domain.Like;

public interface LikeService{

    /**
     * 用户是否点赞该文章
     *
     * @param id
     * @param id1
     * @return
     */
    Like getByUserIdAndQuestionId(Integer userId, Integer questionId);

    /**
     * 点赞
     * @param like
     */
    Integer like(Like like);
}
