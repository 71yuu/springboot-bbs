package com.bbs.service;

import com.bbs.domain.Feedback;
import com.bbs.dto.FeedbackVO;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;

public interface FeedbackService{


    /**
     * 保存投诉反馈
     *
     * @param feedback
     */
    void save(Feedback feedback);

    /**
     * 后台分页列表
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<FeedbackVO> feedbackListPage(int start, int length);

    /**
     * 分页列表
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<FeedbackVO> listPage(int start, int length);

    /**
     * 回复反馈
     *
     * @param feedback
     */
    void update(Integer id, String reply);
}
