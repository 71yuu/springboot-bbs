package com.bbs.service.impl;

import com.bbs.domain.*;
import com.bbs.dto.*;
import com.bbs.enums.CommonConstant;
import com.bbs.mapper.NotificationMapper;
import com.bbs.mapper.UserMapper;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bbs.mapper.FeedbackMapper;
import com.bbs.service.FeedbackService;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class FeedbackServiceImpl implements FeedbackService{

    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public void save(Feedback feedback) {
        feedbackMapper.insert(feedback);
    }

    @Override
    public PageInfo<FeedbackVO> feedbackListPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 构建查询条件
        Example example = new Example(Feedback.class);
        example.createCriteria().andEqualTo("status", CommonConstant.FEEDBACK_TO);

        // 分页查询
        com.github.pagehelper.PageInfo<Feedback> feedbackPageInfo = new com.github.pagehelper.PageInfo<>(feedbackMapper.selectByExample(example));
        List<Feedback> feedbacks = feedbackPageInfo.getList();

        // 转换为 VO
        List<FeedbackVO> feedbackVOS = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            FeedbackVO feedbackVO = new FeedbackVO();
            BeanUtils.copyProperties(feedback, feedbackVO);
            // 设置反馈用户
            User feedbackUser = userMapper.selectByPrimaryKey(feedback.getUserId());
            feedbackVO.setUser(feedbackUser);
            feedbackVOS.add(feedbackVO);
        }

        // 创建分页对象
        PageInfo<FeedbackVO> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)feedbackPageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)feedbackPageInfo.getTotal());
        pageInfo.setData(feedbackVOS);
        return pageInfo;
    }

    @Override
    public PageInfo<FeedbackVO> listPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 构建查询条件
        Example example = new Example(Feedback.class);
        example.createCriteria().andEqualTo("status", CommonConstant.FEEDBACK_ON);

        // 分页查询
        com.github.pagehelper.PageInfo<Feedback> feedbackPageInfo = new com.github.pagehelper.PageInfo<>(feedbackMapper.selectByExample(example));
        List<Feedback> feedbacks = feedbackPageInfo.getList();

        // 转换为 VO
        List<FeedbackVO> feedbackVOS = new ArrayList<>();
        for (Feedback feedback : feedbacks) {
            FeedbackVO feedbackVO = new FeedbackVO();
            BeanUtils.copyProperties(feedback, feedbackVO);
            // 设置反馈用户
            User feedbackUser = userMapper.selectByPrimaryKey(feedback.getUserId());
            feedbackVO.setUser(feedbackUser);
            feedbackVOS.add(feedbackVO);
        }

        // 创建分页对象
        PageInfo<FeedbackVO> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)feedbackPageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)feedbackPageInfo.getTotal());
        pageInfo.setData(feedbackVOS);
        return pageInfo;
    }

    @Override
    public void update(Integer id, String reply) {
        Feedback dataFeedback = feedbackMapper.selectByPrimaryKey(id);
        dataFeedback.setReply(reply);
        dataFeedback.setStatus(CommonConstant.FEEDBACK_ON);
        dataFeedback.setProcessTime(new Date());
        feedbackMapper.updateByPrimaryKeySelective(dataFeedback);

        // 发送回复通知
        Notification notification = new Notification();
        notification.setServiceId(id);
        notification.setReceiver(dataFeedback.getUserId());
        notification.setType(CommonConstant.NOTIFICATION_FEEDBACK);
        notification.setCreateTime(new Date());
        notificationMapper.insert(notification);
    }
}
