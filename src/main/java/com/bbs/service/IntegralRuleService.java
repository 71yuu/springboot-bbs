package com.bbs.service;

import com.bbs.domain.IntegralRule;
import com.bbs.dto.PageInfo;
import com.bbs.dto.SignVO;

public interface IntegralRuleService{


    /**
     * 分页
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<IntegralRule> listPage(int start, int length);

    /**
     * 根据 ID 查询规则
     *
     * @param id
     * @return
     */
    IntegralRule getById(Integer id);

    /**
     * 保存规则
     *
     * @param integralRule
     */
    void save(IntegralRule integralRule);
}
