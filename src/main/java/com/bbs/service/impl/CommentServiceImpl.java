package com.bbs.service.impl;

import com.bbs.domain.*;
import com.bbs.dto.CommentVO;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;
import com.bbs.dto.UserVO;
import com.bbs.enums.CommonConstant;
import com.bbs.mapper.*;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.bbs.service.CommentService;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService{

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private GradeMapper gradeMapper;

    @Override
    public List<CommentVO> getByQuestion(QuestionVO questionVO, User user) {
        // 查询该问题的父评论列表
        Example commentExample = new Example(Comment.class);
        commentExample.createCriteria().andEqualTo("questionId", questionVO.getId())
                .andEqualTo("parentId", 0).andEqualTo("delFlag", CommonConstant.DEL_FLAG_NORMAL);
        commentExample.orderBy("createTime").desc();

        List<Comment> comments = commentMapper.selectByExample(commentExample);
        List<CommentVO> commentVOS = new ArrayList<>();
        for (Comment comment : comments) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);

            // 设置评论人
            User commentUser = userMapper.selectByPrimaryKey(comment.getUserId());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(commentUser, userVO);
            userVO.setGrade(grade);
            commentVO.setUser(userVO);

            // 当前登录用户是否点赞
            if (user != null) {
                Example likeExample = new Example(Like.class);
                likeExample.createCriteria().andEqualTo("userId", user.getId())
                        .andEqualTo("likeType", CommonConstant.LIKE_COMMENT)
                        .andEqualTo("likeId", comment.getId());
                Like like = likeMapper.selectOneByExample(likeExample);
                if (like != null) {
                    commentVO.setLiked(true);
                }
            }

            // 点赞数量
            Example likeExample = new Example(Like.class);
            likeExample.createCriteria().andEqualTo("likeType", CommonConstant.LIKE_COMMENT)
                    .andEqualTo("likeId", comment.getId());
            List<Like> likes = likeMapper.selectByExample(likeExample);
            commentVO.setLikeCount(likes.size());

            // 子评论数量
            Example commentExampleTwo = new Example(Comment.class);
            commentExampleTwo.createCriteria().andEqualTo("parentId", comment.getId());
            List<Comment> subComments = commentMapper.selectByExample(commentExampleTwo);
            commentVO.setCommentCount(subComments.size());
            commentVOS.add(commentVO);
        }

        return commentVOS;
    }

    @Override
    public void save(Comment comment) {
        comment.setCreateTime(new Date());
        commentMapper.insert(comment);
    }

    @Override
    public List<CommentVO> getByCommentId(Integer commentId) {
        Example commentExample = new Example(Comment.class);
        commentExample.createCriteria().andEqualTo("parentId", commentId).andEqualTo("delFlag", CommonConstant.DEL_FLAG_NORMAL);
        List<Comment> comments = commentMapper.selectByExample(commentExample);

        List<CommentVO> commentVOS = new ArrayList<>();
        for (Comment comment : comments) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            // 设置评论人
            User commentUser = userMapper.selectByPrimaryKey(comment.getUserId());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(commentUser.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(commentUser, userVO);
            userVO.setGrade(grade);
            commentVO.setUser(userVO);
            commentVOS.add(commentVO);
        }

        return commentVOS;
    }

    @Override
    public Comment getById(Integer parentId) {
        return commentMapper.selectByPrimaryKey(parentId);
    }

    @Override
    public PageInfo<CommentVO> listPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 分页查询
        com.github.pagehelper.PageInfo<Comment> commentPageInfo = new com.github.pagehelper.PageInfo<>(commentMapper.selectAll());
        List<Comment> comments = commentPageInfo.getList();

        // 转换为 VO
        List<CommentVO> commentVOS = new ArrayList<>();
        for (Comment comment : comments) {
            CommentVO commentVO = new CommentVO();
            BeanUtils.copyProperties(comment, commentVO);
            // 设置评论人
            User commentUser = userMapper.selectByPrimaryKey(comment.getUserId());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(commentUser.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(commentUser, userVO);
            userVO.setGrade(grade);
            commentVO.setUser(userVO);
            commentVOS.add(commentVO);

            // 查询问题
            Question question = questionMapper.selectByPrimaryKey(comment.getQuestionId());
            commentVO.setQuestion(question);
        }

        // 创建分页对象
        PageInfo<CommentVO> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)commentPageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)commentPageInfo.getTotal());
        pageInfo.setData(commentVOS);
        return pageInfo;
    }

    @Override
    public void deleteById(Integer id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        comment.setDelFlag(CommonConstant.DEL_FLAG_DEL);
        commentMapper.updateByPrimaryKeySelective(comment);
    }
}
