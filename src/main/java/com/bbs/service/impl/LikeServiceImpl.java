package com.bbs.service.impl;

import com.bbs.domain.Comment;
import com.bbs.domain.Like;
import com.bbs.domain.Notification;
import com.bbs.domain.Question;
import com.bbs.enums.CommonConstant;
import com.bbs.mapper.CommentMapper;
import com.bbs.mapper.NotificationMapper;
import com.bbs.mapper.QuestionMapper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bbs.mapper.LikeMapper;
import com.bbs.service.LikeService;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;

@Service
public class LikeServiceImpl implements LikeService{

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public Like getByUserIdAndQuestionId(Integer userId, Integer questionId) {
        Example example = new Example(Like.class);
        example.createCriteria().andEqualTo("likeType", CommonConstant.LIKE_QUESTION)
                .andEqualTo("likeId", questionId).andEqualTo("userId", userId);
        Like like = likeMapper.selectOneByExample(example);
        return like;
    }

    @Override
    public Integer like(Like like) {
        Example likeExample = new Example(Like.class);
        likeExample.createCriteria().andEqualTo("likeId", like.getLikeId())
                .andEqualTo("likeType", like.getLikeType()).andEqualTo("userId", like.getUserId());
        Like dataLike = likeMapper.selectOneByExample(likeExample);
        // 原来是点赞的，删除该条记录
        if (dataLike != null) {
            likeMapper.deleteByPrimaryKey(dataLike.getId());
            return 202;
        }
        // 原来没有，新增一条记录
        else {
            like.setCreateTime(new Date());
            likeMapper.insert(like);

            // 新增通知
            Notification notification = new Notification();
            notification.setNotifer(like.getUserId());

            // 点赞问题
            if (CommonConstant.LIKE_QUESTION.equals(like.getLikeType())) {
                Question question = questionMapper.selectByPrimaryKey(like.getLikeId());
                notification.setReceiver(question.getCreator());

                // 设置类型
                notification.setType(CommonConstant.NOTIFICATION_QUESTION_LIKE);

                // 业务ID
                notification.setServiceId(question.getId());
            }
            // 点赞评论
            else {
                Comment comment = commentMapper.selectByPrimaryKey(like.getLikeId());
                notification.setReceiver(comment.getUserId());

                // 设置类型
                notification.setType(CommonConstant.NOTIFICATION_COMMENT_LIKE);

                // 业务ID
                notification.setServiceId(comment.getId());
            }

            // 创建时间
            notification.setCreateTime(new Date());

            notificationMapper.insert(notification);
            return 200;
        }
    }
}
