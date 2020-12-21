package com.bbs.service.impl;

import com.bbs.domain.*;
import com.bbs.dto.NotificationVO;
import com.bbs.dto.PageResult;
import com.bbs.enums.CommonConstant;
import com.bbs.mapper.*;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.bbs.service.NotificationService;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private FeedbackMapper feedbackMapper;

    @Override
    public void save(Notification notification) {
        notificationMapper.insert(notification);
    }

    @Override
    public PageResult<NotificationVO> listByCommentUser(Integer id, Integer page, Integer size) {
        // 设置分页查询条件
        PageHelper.startPage(page, size);

        Example example = new Example(Notification.class);
        example.createCriteria().andEqualTo("receiver", id);
        example.and().orEqualTo("type", CommonConstant.NOTIFICATION_COMMENT_COMMENT).orEqualTo("type", CommonConstant.NOTIFICATION_QUESTION_COMMENT);
        example.orderBy("createTime").desc();

        // 分页查询
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notificationMapper.selectByExample(example));
        List<Notification> notifications = notificationPageInfo.getList();
        List<NotificationVO> notificationVOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationVO notificationVO = new NotificationVO();
            BeanUtils.copyProperties(notification, notificationVO);

            // 查询发送者
            User notiferUser = userMapper.selectByPrimaryKey(notification.getNotifer());
            notificationVO.setNotiferUser(notiferUser);

            // 查询接收者
            User receiver = userMapper.selectByPrimaryKey(notification.getReceiver());
            notificationVO.setReceiverUser(receiver);

            // 如果跟评论相关，查询评论
            if (notification.getType().equals(CommonConstant.NOTIFICATION_COMMENT_COMMENT)) {
                Comment comment = commentMapper.selectByPrimaryKey(notification.getServiceId());
                notificationVO.setComment(comment);
                Question question = questionMapper.selectByPrimaryKey(comment.getQuestionId());
                notificationVO.setQuestion(question);
            } else {
                Question question = questionMapper.selectByPrimaryKey(notification.getServiceId());
                notificationVO.setQuestion(question);
            }

            notificationVOS.add(notificationVO);
        }

        // 创建一个分页对象
        PageResult<NotificationVO> pageResult = new PageResult<>();
        // 第几页
        pageResult.setPage(page);
        // 分页数据
        pageResult.setList(notificationVOS);
        // 数据总条数
        Long total = notificationPageInfo.getTotal();
        pageResult.setTotal(total.intValue());
        // 总页数
        int pageTotal = (total.intValue() + size - 1) / size;
        pageResult.setPageTotal(pageTotal);

        return pageResult;
    }

    @Override
    public PageResult<NotificationVO> listByLikeUser(Integer id, Integer page, Integer size) {
        // 设置分页查询条件
        PageHelper.startPage(page, size);

        Example example = new Example(Notification.class);
        example.createCriteria().andEqualTo("receiver", id);
        example.and().orEqualTo("type", CommonConstant.NOTIFICATION_QUESTION_LIKE).orEqualTo("type", CommonConstant.NOTIFICATION_COMMENT_LIKE);
        example.orderBy("createTime").desc();

        // 分页查询
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notificationMapper.selectByExample(example));
        List<Notification> notifications = notificationPageInfo.getList();
        List<NotificationVO> notificationVOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationVO notificationVO = new NotificationVO();
            BeanUtils.copyProperties(notification, notificationVO);

            // 查询发送者
            User notiferUser = userMapper.selectByPrimaryKey(notification.getNotifer());
            notificationVO.setNotiferUser(notiferUser);

            // 查询接收者
            User receiver = userMapper.selectByPrimaryKey(notification.getReceiver());
            notificationVO.setReceiverUser(receiver);

            // 如果跟评论相关，查询评论
            if (notification.getType().equals(CommonConstant.NOTIFICATION_COMMENT_LIKE)) {
                Comment comment = commentMapper.selectByPrimaryKey(notification.getServiceId());
                notificationVO.setComment(comment);
                Question question = questionMapper.selectByPrimaryKey(comment.getQuestionId());
                notificationVO.setQuestion(question);
            } else {
                Question question = questionMapper.selectByPrimaryKey(notification.getServiceId());
                notificationVO.setQuestion(question);
            }


            notificationVOS.add(notificationVO);
        }

        // 创建一个分页对象
        PageResult<NotificationVO> pageResult = new PageResult<>();
        // 第几页
        pageResult.setPage(page);
        // 分页数据
        pageResult.setList(notificationVOS);
        // 数据总条数
        Long total = notificationPageInfo.getTotal();
        pageResult.setTotal(total.intValue());
        // 总页数
        int pageTotal = (total.intValue() + size - 1) / size;
        pageResult.setPageTotal(pageTotal);

        return pageResult;
    }

    @Override
    public PageResult<NotificationVO> listByAuditUser(Integer id, Integer page, Integer size) {
        // 设置分页查询条件
        PageHelper.startPage(page, size);

        Example example = new Example(Notification.class);
        example.createCriteria().andEqualTo("receiver", id);
        example.and().orEqualTo("type", CommonConstant.NOTIFICATION_QUESTION_AUDIT).orEqualTo("type", CommonConstant.NOTIFICATION_QUESTION_AUDIT_FAIL);
        example.orderBy("createTime").desc();

        // 分页查询
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notificationMapper.selectByExample(example));
        List<Notification> notifications = notificationPageInfo.getList();
        List<NotificationVO> notificationVOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationVO notificationVO = new NotificationVO();
            BeanUtils.copyProperties(notification, notificationVO);

            // 查询发送者
            User notiferUser = userMapper.selectByPrimaryKey(notification.getNotifer());
            notificationVO.setNotiferUser(notiferUser);

            // 查询接收者
            User receiver = userMapper.selectByPrimaryKey(notification.getReceiver());
            notificationVO.setReceiverUser(receiver);

            // 如果跟评论相关，查询评论
            if (notification.getType().equals(CommonConstant.NOTIFICATION_COMMENT_LIKE) || notification.getType().equals(CommonConstant.NOTIFICATION_COMMENT_COMMENT)) {
                Comment comment = commentMapper.selectByPrimaryKey(notification.getServiceId());
                notificationVO.setComment(comment);
                Question question = questionMapper.selectByPrimaryKey(comment.getQuestionId());
                notificationVO.setQuestion(question);
            } else {
                Question question = questionMapper.selectByPrimaryKey(notification.getServiceId());
                notificationVO.setQuestion(question);
            }

            notificationVOS.add(notificationVO);
        }

        // 创建一个分页对象
        PageResult<NotificationVO> pageResult = new PageResult<>();
        // 第几页
        pageResult.setPage(page);
        // 分页数据
        pageResult.setList(notificationVOS);
        // 数据总条数
        Long total = notificationPageInfo.getTotal();
        pageResult.setTotal(total.intValue());
        // 总页数
        int pageTotal = (total.intValue() + size - 1) / size;
        pageResult.setPageTotal(pageTotal);

        return pageResult;
    }

    @Override
    public PageResult<NotificationVO> listByFeedbackUser(Integer id, Integer page, Integer size) {
        // 设置分页查询条件
        PageHelper.startPage(page, size);

        Example example = new Example(Notification.class);
        example.createCriteria().andEqualTo("receiver", id);
        example.and().andEqualTo("type", CommonConstant.NOTIFICATION_FEEDBACK);
        example.orderBy("createTime").desc();

        // 分页查询
        PageInfo<Notification> notificationPageInfo = new PageInfo<>(notificationMapper.selectByExample(example));
        List<Notification> notifications = notificationPageInfo.getList();
        List<NotificationVO> notificationVOS = new ArrayList<>();
        for (Notification notification : notifications) {
            NotificationVO notificationVO = new NotificationVO();
            BeanUtils.copyProperties(notification, notificationVO);

            // 查询回复
            Feedback feedback = feedbackMapper.selectByPrimaryKey(notification.getServiceId());
            notificationVO.setFeedback(feedback);
            notificationVOS.add(notificationVO);
        }

        // 创建一个分页对象
        PageResult<NotificationVO> pageResult = new PageResult<>();
        // 第几页
        pageResult.setPage(page);
        // 分页数据
        pageResult.setList(notificationVOS);
        // 数据总条数
        Long total = notificationPageInfo.getTotal();
        pageResult.setTotal(total.intValue());
        // 总页数
        int pageTotal = (total.intValue() + size - 1) / size;
        pageResult.setPageTotal(pageTotal);

        return pageResult;
    }
}
