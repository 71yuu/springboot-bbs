package com.bbs.service.impl;

import com.bbs.constant.IntegralRuleConstant;
import com.bbs.domain.*;
import com.bbs.dto.CommentVO;
import com.bbs.dto.PageInfo;
import com.bbs.enums.CommonConstant;
import com.bbs.mapper.*;
import com.bbs.service.GradeService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

import com.bbs.service.UserService;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    @Resource
    private UserMapper userMapper;

    @Resource
    private GradeMapper gradeMapper;

    @Resource
    private IntegralRuleMapper integralRuleMapper;

    @Resource
    private QuestionMapper questionMapper;

    @Resource
    private NotificationMapper notificationMapper;

    @Override
    public User getByUsername(String username) {
        Example example = new Example(User.class);
        example.createCriteria().andEqualTo("username", username);
        User user = userMapper.selectOneByExample(example);
        return user;
    }

    @Override
    public void register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setCreateTime(new Date());
        user.setDelFlag(CommonConstant.DEL_FLAG_NORMAL);
        user.setIntegral(0);
        user.setGradeId(1);
        // 设置默认头像
        user.setHeadUrl("/img/anonymous.jpg");
        userMapper.insert(user);
    }

    @Override
    public User login(String username, String password) {
        User user = getByUsername(username);
        // 未找到该用户名的用户
        if (user == null) {
            return null;
        }

        // 密码匹配成功
        if (user.getPassword().equals(password)) {
            return user;
        } else {
            return null;
        }
    }

    @Override
    public void update(User user) {
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public PageInfo<User> listPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 分页查询
        com.github.pagehelper.PageInfo<User> userPageInfo = new com.github.pagehelper.PageInfo<>(userMapper.selectAll());
        List<User> users = userPageInfo.getList();

        // 创建分页对象
        PageInfo<User> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)userPageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)userPageInfo.getTotal());
        pageInfo.setData(users);
        return pageInfo;
    }

    @Override
    public void deleteById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setDelFlag(CommonConstant.DEL_FLAG_DEL);
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public User getById(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        return user;
    }

    @Override
    public void updateGrade(Integer id) {
        User user = userMapper.selectByPrimaryKey(id);
        // 获取用户的等级
        Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
        // 判断积分是否超过当前等级
        if (grade.getGrade() < 8) {
            // 更新用户等级
            // 查询下个等级的ID
            Integer currGrade = grade.getGrade();
            ++currGrade;
            // 查询
            Example example = new Example(Grade.class);
            example.createCriteria().andEqualTo("grade", currGrade);
            Grade nextGrade = gradeMapper.selectOneByExample(example);

            if (user.getIntegral() >= nextGrade.getScore()) {
                user.setGradeId(nextGrade.getId());
                // 更新
                userMapper.updateByPrimaryKey(user);
            }
        }
    }

    @Override
    public void addIntegral(String integralType, Integer id) {
        // 根据积分规则类型查询积分
        Example example = new Example(IntegralRule.class);
        example.createCriteria().andEqualTo("ruleFlag", integralType);
        IntegralRule integralRule = integralRuleMapper.selectOneByExample(example);

        // 发布问题
        if (IntegralRuleConstant.INTEGRAL_RULE_FBWT.equals(integralType)) {
            // 查询用户今日发布的问题，并且审核通过的
            Example questionExample = new Example(Question.class);
            questionExample.createCriteria().andEqualTo("creator", id).andLike("createTime", LocalDate.now().toString() + "%")
                    .andEqualTo("state", 1);
            List<Question> questions = questionMapper.selectByExample(questionExample);
            if (questions.size() < integralRule.getDailyLimit()) {
                // 增加积分
                integral(id, integralRule.getIntegral());
            }
        }

        // 问题收到评论
        else if (IntegralRuleConstant.INTEGRAL_RULE_WTSDPL.equals(integralType)) {
            // 用户今天文章收到的评论数
            Example notificationExample = new Example(Notification.class);
            notificationExample.createCriteria().andEqualTo("type", CommonConstant.NOTIFICATION_QUESTION_COMMENT)
                    .andEqualTo("receiver", id).andNotEqualTo("notifer", id).andLike("createTime", LocalDate.now().toString() + "%");
            List<Notification> notifications = notificationMapper.selectByExample(notificationExample);
            if (notifications.size() < integralRule.getDailyLimit()) {
                // 增加积分
                integral(id, integralRule.getIntegral());
            }
        }

        // 发表评论
        else if (IntegralRuleConstant.INTEGRAL_RULE_FBPL.equals(integralType)) {
            // 用户今天发表的评论
            Example noticationExample = new Example(Notification.class);
            noticationExample.createCriteria().andEqualTo("type", CommonConstant.NOTIFICATION_QUESTION_COMMENT)
                    .andEqualTo("notifer", id).andNotEqualTo("receiver", id).andLike("createTime", LocalDate.now().toString() + "%");
            List<Notification> notifications = notificationMapper.selectByExample(noticationExample);
            if (notifications.size() < integralRule.getDailyLimit()) {
                // 增加积分
                integral(id, integralRule.getIntegral());
            }
        }
    }

    /**
     * 增加积分
     *
     * @param id
     * @param integeral
     */
    @Override
    public void integral(Integer id, Integer integeral) {
        User user = userMapper.selectByPrimaryKey(id);
        user.setIntegral(user.getIntegral() + integeral);
        // 获取用户的等级
        Grade grade = gradeMapper.selectByPrimaryKey(user.getGradeId());
        // 判断积分是否超过当前等级
        if (grade.getGrade() < 8) {
            // 更新用户等级
            // 查询下个等级的ID
            Integer currGrade = grade.getGrade();
            ++currGrade;
            // 查询
            Example example = new Example(Grade.class);
            example.createCriteria().andEqualTo("grade", currGrade);
            Grade nextGrade = gradeMapper.selectOneByExample(example);

            if (user.getIntegral() >= nextGrade.getScore()) {
                user.setGradeId(nextGrade.getId());
            }
        }
        // 更新
        userMapper.updateByPrimaryKey(user);
    }
}
