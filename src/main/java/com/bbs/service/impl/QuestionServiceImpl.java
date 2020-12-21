package com.bbs.service.impl;

import com.bbs.domain.*;
import com.bbs.dto.PageResult;
import com.bbs.dto.QuestionVO;
import com.bbs.dto.UserVO;
import com.bbs.enums.CommonConstant;
import com.bbs.mapper.*;
import com.bbs.util.ImgUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.bbs.service.QuestionService;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class QuestionServiceImpl implements QuestionService{

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private CommentMapper commentMapper;

    @Resource
    private LikeMapper likeMapper;

    @Resource
    private NotificationMapper notificationMapper;

    @Resource
    private GradeMapper gradeMapper;

    @Override
    public PageResult<QuestionVO> page(String search, Integer catId, Integer page, Integer size) {
        // 设置分页查询条件
        PageHelper.startPage(page, size);

        // 构建查询条件
        Example example = new Example(Question.class);
        Example.Criteria criteria = example.createCriteria();
        if (StringUtil.isNotEmpty(search)) {
            criteria.andLike("title", "%" + search + "%");
        }
        if (catId != null) {
            criteria.andEqualTo("catId", catId);
        }
        criteria.andEqualTo("state", CommonConstant.QUESTION_YES_AUDIT).andEqualTo("delFlag", CommonConstant.DEL_FLAG_NORMAL);
        example.orderBy("createTime").desc();

        // 分页查询
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.selectByExample(example));
        List<Question> questions = questionPageInfo.getList();

        // 转换为 VO
        List<QuestionVO> questionVOS = new ArrayList<>();
        for (Question question : questions) {
            QuestionVO questionVO = new QuestionVO();
            // 将 question 复制到 questionVo
            BeanUtils.copyProperties(question, questionVO);
            // 查询用户信息
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setGrade(grade);
            questionVO.setUser(userVO);
            // 查询评论数
            Example commentExample = new Example(Comment.class);
            commentExample.createCriteria().andEqualTo("questionId", question.getId());
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            questionVO.setCommentCount(comments.size());
            // 解析图片
            List<String> imgs = ImgUtil.getImgSrc(question.getContent());
            questionVO.setImgs(imgs);
            // 解析描述
            String desc = question.getText();
            if (desc.length() >= 43) {
                desc = desc.substring(0, 43) + "...";
            }
            questionVO.setDesc(desc);
            questionVOS.add(questionVO);
        }

        // 创建一个分页对象
        PageResult<QuestionVO> pageResult = new PageResult<>();
        // 第几页
        pageResult.setPage(page);
        // 分页数据
        pageResult.setList(questionVOS);
        // 数据总条数
        Long total = questionPageInfo.getTotal();
        pageResult.setTotal(total.intValue());
        // 总页数
        int pageTotal = (total.intValue() + size - 1) / size;
        pageResult.setPageTotal(pageTotal);

        return pageResult;
    }

    @Override
    public void save(Question question) {
        question.setCreateTime(new Date());
        questionMapper.insert(question);
    }

    @Override
    public PageResult<QuestionVO> listByUser(Integer id, Integer page, Integer size) {
        // 设置分页查询条件
        PageHelper.startPage(page, size);

        // 构建查询条件
        Example example = new Example(Question.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("creator", id).andEqualTo("state", CommonConstant.QUESTION_YES_AUDIT).andEqualTo("delFlag", CommonConstant.DEL_FLAG_NORMAL);
        example.orderBy("createTime").desc();

        // 分页查询
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.selectByExample(example));
        List<Question> questions = questionPageInfo.getList();

        // 转换为 VO
        List<QuestionVO> questionVOS = new ArrayList<>();
        for (Question question : questions) {
            QuestionVO questionVO = new QuestionVO();
            // 将 question 复制到 questionVo
            BeanUtils.copyProperties(question, questionVO);
            // 查询用户信息
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setGrade(grade);
            questionVO.setUser(userVO);
            // 查询评论数
            Example commentExample = new Example(Comment.class);
            commentExample.createCriteria().andEqualTo("questionId", question.getId());
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            questionVO.setCommentCount(comments.size());
            // 解析图片
            List<String> imgs = ImgUtil.getImgSrc(question.getContent());
            questionVO.setImgs(imgs);
            // 解析描述
            String desc = question.getText();
            if (desc.length() >= 43) {
                desc = desc.substring(0, 43) + "...";
            }
            questionVO.setDesc(desc);
            questionVOS.add(questionVO);
        }

        // 创建一个分页对象
        PageResult<QuestionVO> pageResult = new PageResult<>();
        // 第几页
        pageResult.setPage(page);
        // 分页数据
        pageResult.setList(questionVOS);
        // 数据总条数
        Long total = questionPageInfo.getTotal();
        pageResult.setTotal(total.intValue());
        // 总页数
        int pageTotal = (total.intValue() + size - 1) / size;
        pageResult.setPageTotal(pageTotal);

        return pageResult;
    }

    @Override
    public QuestionVO getById(Integer id) {
        Example example = new Example(Question.class);
        example.createCriteria().andEqualTo("id", id).andEqualTo("delFlag", CommonConstant.DEL_FLAG_NORMAL);
        Question question = questionMapper.selectOneByExample(example);
        if (question != null) {
            QuestionVO questionVO = new QuestionVO();
            // 将 question 复制到 questionVo
            BeanUtils.copyProperties(question, questionVO);
            // 查询用户信息
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setGrade(grade);
            questionVO.setUser(userVO);
            // 查询评论数
            Example commentExample = new Example(Comment.class);
            commentExample.createCriteria().andEqualTo("questionId", question.getId());
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            questionVO.setCommentCount(comments.size());
            // 解析图片
            List<String> imgs = ImgUtil.getImgSrc(question.getContent());
            questionVO.setImgs(imgs);
            // 解析描述
            String desc = question.getText();
            if (desc.length() >= 43) {
                desc = desc.substring(0, 43) + "...";
            }
            questionVO.setDesc(desc);
            return questionVO;
        }
        return null;
    }

    @Override
    public void deleteById(Integer questionId) {
        Question question = questionMapper.selectByPrimaryKey(questionId);
        question.setDelFlag(CommonConstant.DEL_FLAG_DEL);
        questionMapper.updateByPrimaryKeySelective(question);
    }

    @Override
    public void browse(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        question.setViewCount(question.getViewCount() + 1);
        questionMapper.updateByPrimaryKeySelective(question);
    }

    @Override
    public com.bbs.dto.PageInfo<QuestionVO> auditListPage(Integer page, Integer pageSize) {
        // 设置分页查询条件
        PageHelper.startPage(page, pageSize);

        // 构建查询条件
        Example example = new Example(Question.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("state", CommonConstant.QUESTION_TO_AUDIT);
        example.orderBy("createTime").desc();

        // 分页查询
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.selectByExample(example));
        List<Question> questions = questionPageInfo.getList();

        // 转换为 VO
        List<QuestionVO> questionVOS = new ArrayList<>();
        for (Question question : questions) {
            QuestionVO questionVO = new QuestionVO();
            // 将 question 复制到 questionVo
            BeanUtils.copyProperties(question, questionVO);
            // 查询用户信息
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setGrade(grade);
            questionVO.setUser(userVO);
            // 查询评论数
            Example commentExample = new Example(Comment.class);
            commentExample.createCriteria().andEqualTo("questionId", question.getId());
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            questionVO.setCommentCount(comments.size());
            // 解析图片
            List<String> imgs = ImgUtil.getImgSrc(question.getContent());
            questionVO.setImgs(imgs);
            // 解析描述
            String desc = question.getText();
            if (desc.length() >= 43) {
                desc = desc.substring(0, 43) + "...";
            }
            questionVO.setDesc(desc);
            questionVOS.add(questionVO);
        }

        // 创建分页对象
        com.bbs.dto.PageInfo<QuestionVO> pageInfo = new  com.bbs.dto.PageInfo<>();
        pageInfo.setRecordsTotal((int)questionPageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)questionPageInfo.getTotal());
        pageInfo.setData(questionVOS);
        return pageInfo;
    }

    @Override
    public com.bbs.dto.PageInfo<QuestionVO> listPage(int page, int pageSize) {
        // 设置分页查询条件
        PageHelper.startPage(page, pageSize);

        // 构建查询条件
        Example example = new Example(Question.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.orEqualTo("state", CommonConstant.QUESTION_NO_AUDIT).orEqualTo("state", CommonConstant.QUESTION_YES_AUDIT);
        example.orderBy("createTime").desc();

        // 分页查询
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.selectByExample(example));
        List<Question> questions = questionPageInfo.getList();

        // 转换为 VO
        List<QuestionVO> questionVOS = new ArrayList<>();
        for (Question question : questions) {
            QuestionVO questionVO = new QuestionVO();
            // 将 question 复制到 questionVo
            BeanUtils.copyProperties(question, questionVO);
            // 查询用户信息
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setGrade(grade);
            questionVO.setUser(userVO);
            // 查询评论数
            Example commentExample = new Example(Comment.class);
            commentExample.createCriteria().andEqualTo("questionId", question.getId());
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            questionVO.setCommentCount(comments.size());
            // 解析图片
            List<String> imgs = ImgUtil.getImgSrc(question.getContent());
            questionVO.setImgs(imgs);
            // 解析描述
            String desc = question.getText();
            if (desc.length() >= 43) {
                desc = desc.substring(0, 43) + "...";
            }
            questionVO.setDesc(desc);
            questionVOS.add(questionVO);
        }

        // 创建分页对象
        com.bbs.dto.PageInfo<QuestionVO> pageInfo = new  com.bbs.dto.PageInfo<>();
        pageInfo.setRecordsTotal((int)questionPageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)questionPageInfo.getTotal());
        pageInfo.setData(questionVOS);
        return pageInfo;
    }

    @Override
    public void yesAudit(Integer id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        question.setState(CommonConstant.QUESTION_YES_AUDIT);
        questionMapper.updateByPrimaryKeySelective(question);

        // 增加通知
        Notification notification = new Notification();
        notification.setReceiver(question.getCreator());
        notification.setCreateTime(new Date());
        notification.setType(CommonConstant.NOTIFICATION_QUESTION_AUDIT);
        notification.setServiceId(question.getId());
        notificationMapper.insert(notification);
    }

    @Override
    public void noAudit(String id) {
        Question question = questionMapper.selectByPrimaryKey(id);
        question.setState(CommonConstant.QUESTION_NO_AUDIT);
        questionMapper.updateByPrimaryKeySelective(question);

        // 增加通知
        Notification notification = new Notification();
        notification.setReceiver(question.getCreator());
        notification.setCreateTime(new Date());
        notification.setType(CommonConstant.NOTIFICATION_QUESTION_AUDIT_FAIL);
        notification.setServiceId(question.getId());
        notificationMapper.insert(notification);
    }

    @Override
    public void edit(Question question) {
        Question dataQuestion = questionMapper.selectByPrimaryKey(question.getId());
        dataQuestion.setState(CommonConstant.QUESTION_TO_AUDIT);
        dataQuestion.setTitle(question.getTitle());
        dataQuestion.setContent(question.getContent());
        dataQuestion.setText(question.getText());
        dataQuestion.setCatId(question.getCatId());
        questionMapper.updateByPrimaryKeySelective(dataQuestion);
    }

    @Override
    public List<QuestionVO> getHot() {
        // 设置分页查询条件
        PageHelper.startPage(1, 5);
        // 查询浏览量最多的5条
        Example questionExample = new Example(Question.class);
        questionExample.createCriteria().andEqualTo("delFlag", 0);
        questionExample.orderBy("viewCount").desc();
        PageInfo<Question> questionPageInfo = new PageInfo<>(questionMapper.selectByExample(questionExample));
        List<Question> questions = questionPageInfo.getList();
        // 转换为 VO
        List<QuestionVO> questionVOS = new ArrayList<>();
        for (Question question : questions) {
            QuestionVO questionVO = new QuestionVO();
            // 将 question 复制到 questionVo
            BeanUtils.copyProperties(question, questionVO);
            // 查询用户信息
            User user = userMapper.selectByPrimaryKey(question.getCreator());
            // 查询用户等级
            Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
            UserVO userVO = new UserVO();
            BeanUtils.copyProperties(user, userVO);
            userVO.setGrade(grade);
            questionVO.setUser(userVO);
            // 查询评论数
            Example commentExample = new Example(Comment.class);
            commentExample.createCriteria().andEqualTo("questionId", question.getId());
            List<Comment> comments = commentMapper.selectByExample(commentExample);
            questionVO.setCommentCount(comments.size());
            // 解析图片
            List<String> imgs = ImgUtil.getImgSrc(question.getContent());
            questionVO.setImgs(imgs);
            // 解析描述
            String desc = question.getText();
            if (desc.length() >= 43) {
                desc = desc.substring(0, 43) + "...";
            }
            questionVO.setDesc(desc);
            questionVOS.add(questionVO);
        }
        return questionVOS;
    }

}
