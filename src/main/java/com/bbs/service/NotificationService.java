package com.bbs.service;

import com.bbs.domain.Notification;
import com.bbs.dto.NotificationVO;
import com.bbs.dto.PageResult;

public interface NotificationService{

    /**
     * 保存通知
     *
     * @param notification
     */
    void save(Notification notification);

    /**
     * 分页查询评论通知
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    PageResult<NotificationVO> listByCommentUser(Integer id, Integer page, Integer size);

    /**
     * 分页查询收到的赞
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    PageResult<NotificationVO> listByLikeUser(Integer id, Integer page, Integer size);

    /**
     * 查询用户文章审核
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    PageResult<NotificationVO> listByAuditUser(Integer id, Integer page, Integer size);

    /**
     * 投诉反馈回复
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    PageResult<NotificationVO> listByFeedbackUser(Integer id, Integer page, Integer size);
}
