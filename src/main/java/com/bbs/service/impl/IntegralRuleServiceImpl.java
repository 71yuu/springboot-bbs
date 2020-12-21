package com.bbs.service.impl;

import com.bbs.domain.Grade;
import com.bbs.domain.IntegralRule;
import com.bbs.dto.PageInfo;
import com.bbs.dto.SignVO;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bbs.mapper.IntegralRuleMapper;
import com.bbs.service.IntegralRuleService;

import java.util.List;

@Service
public class IntegralRuleServiceImpl implements IntegralRuleService{

    @Resource
    private IntegralRuleMapper integralRuleMapper;

    @Override
    public PageInfo<IntegralRule> listPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 分页查询
        com.github.pagehelper.PageInfo<IntegralRule> integralRulePageInfo = new com.github.pagehelper.PageInfo<>(integralRuleMapper.selectAll());
        List<IntegralRule> integralRules = integralRulePageInfo.getList();

        // 创建分页对象
        PageInfo<IntegralRule> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)integralRulePageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)integralRulePageInfo.getTotal());
        pageInfo.setData(integralRules);
        return pageInfo;
    }

    @Override
    public IntegralRule getById(Integer id) {
        return integralRuleMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(IntegralRule integralRule) {
        IntegralRule currIntegralRule = integralRuleMapper.selectByPrimaryKey(integralRule.getId());
        currIntegralRule.setDailyLimit(integralRule.getDailyLimit());
        currIntegralRule.setIntegral(integralRule.getIntegral());
        integralRuleMapper.updateByPrimaryKeySelective(currIntegralRule);
    }
}
