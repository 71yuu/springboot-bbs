package com.bbs.service.impl;

import com.bbs.constant.IntegralRuleConstant;
import com.bbs.domain.*;
import com.bbs.dto.PageInfo;
import com.bbs.dto.SignVO;
import com.bbs.mapper.IntegralRuleMapper;
import com.bbs.mapper.UserMapper;
import com.bbs.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bbs.mapper.SignMapper;
import com.bbs.service.SignService;
import tk.mybatis.mapper.entity.Example;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class SignServiceImpl implements SignService {

    @Resource
    private SignMapper signMapper;

    @Resource
    private IntegralRuleMapper integralRuleMapper;

    @Resource
    private UserMapper userMapper;

    @Resource
    private UserService userService;

    @Override
    public Sign todaySign(Integer userId) {
        // 获取今天的日期
        LocalDate localDate = LocalDate.now();

        // 查询用户今天的签到
        Example example = new Example(Sign.class);
        example.createCriteria().andEqualTo("signUser", userId).andLike("signTime", localDate.toString() + "%");
        Sign sign = signMapper.selectOneByExample(example);

        return sign;
    }

    @Override
    public List<Integer> monthSign(Integer id) {
        // 获取今天的日期
        LocalDate localDate = LocalDate.now();
        // 获取年份
        int year = localDate.getYear();
        int month = localDate.getMonth().getValue();
        String date = year + "-" + month;

        // 查询用户本月的签到
        Example example = new Example(Sign.class);
        example.createCriteria().andEqualTo("signUser", id).andLike("signTime", date + "%");
        List<Sign> signs = signMapper.selectByExample(example);

        // 获取天数
        List<Integer> days = new ArrayList<>();
        signs.forEach(s -> {
            Date signTime = s.getSignTime();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(signTime);
            int day = calendar.get(Calendar.DAY_OF_MONTH) - 1;
            days.add(day);
        });

        return days;
    }

    @Override
    public Sign sign(Integer id) {
        // 获取昨天的日期
        LocalDate localDate = LocalDate.now().plusDays(-1);
        // 查询用户昨天的签到
        Example example = new Example(Sign.class);
        example.createCriteria().andEqualTo("signUser", id).andLike("signTime", localDate.toString() + "%");
        Sign preSign = signMapper.selectOneByExample(example);
        Sign sign = new Sign();
        sign.setSignUser(id);
        sign.setSignTime(new Date());
        if (preSign != null) {
            // 判断是否连续签到7天以上
            Integer continuitySign = preSign.getContinuitySign();
            ++continuitySign;
            sign.setContinuitySign(continuitySign);
            if (continuitySign >= 7) {
                // 查询签到连续7天以上的积分
                Example integralRuleExample = new Example(IntegralRule.class);
                integralRuleExample.createCriteria().andEqualTo("ruleFlag", IntegralRuleConstant.INTEGRAL_RULE_LXQD);
                IntegralRule integralRule = integralRuleMapper.selectOneByExample(integralRuleExample);
                sign.setSignIntegral(integralRule.getIntegral());
            } else {
                // 查询正常的签到积分
                Example integralRuleExample = new Example(IntegralRule.class);
                integralRuleExample.createCriteria().andEqualTo("ruleFlag", IntegralRuleConstant.INTEGRAL_RULE_QD);
                IntegralRule integralRule = integralRuleMapper.selectOneByExample(integralRuleExample);
                sign.setSignIntegral(integralRule.getIntegral());
            }
        } else {
            sign.setContinuitySign(1);

            // 查询是否首次签到
            Example signExample = new Example(Sign.class);
            signExample.createCriteria().andEqualTo("signUser", id);
            List<Sign> signs = signMapper.selectByExample(signExample);
            // 首次签到
            if (signs.size() == 0) {
                // 查询首次签到的积分
                Example integralRuleExample = new Example(IntegralRule.class);
                integralRuleExample.createCriteria().andEqualTo("ruleFlag", IntegralRuleConstant.INTEGRAL_RULE_SCQD);
                IntegralRule integralRule = integralRuleMapper.selectOneByExample(integralRuleExample);
                sign.setSignIntegral(integralRule.getIntegral());
            } else {
                // 查询正常的签到积分
                Example integralRuleExample = new Example(IntegralRule.class);
                integralRuleExample.createCriteria().andEqualTo("ruleFlag", IntegralRuleConstant.INTEGRAL_RULE_QD);
                IntegralRule integralRule = integralRuleMapper.selectOneByExample(integralRuleExample);
                sign.setSignIntegral(integralRule.getIntegral());
            }
        }

        // 保存签到记录
        signMapper.insert(sign);

        // 增加用户的积分
       userService.integral(id, sign.getSignIntegral());

        return sign;
    }

    @Override
    public PageInfo<SignVO> listPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 分页查询
        com.github.pagehelper.PageInfo<Sign> signPageInfo = new com.github.pagehelper.PageInfo<>(signMapper.selectAll());
        List<Sign> signs = signPageInfo.getList();

        List<SignVO> signVOS = new ArrayList<>();
        signs.forEach(s -> {
            SignVO signVO = new SignVO();
            BeanUtils.copyProperties(s, signVO);

            // 查询用户
            User user = userMapper.selectByPrimaryKey(s.getSignUser());
            signVO.setUser(user);
            signVOS.add(signVO);
        });

        // 创建分页对象
        PageInfo<SignVO> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)signPageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)signPageInfo.getTotal());
        pageInfo.setData(signVOS);
        return pageInfo;
    }
}

