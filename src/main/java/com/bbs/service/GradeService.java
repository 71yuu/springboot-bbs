package com.bbs.service;

import com.bbs.domain.Grade;
import com.bbs.dto.PageInfo;

/**
 * @ClassName: GradeService
 * @Auther: Yuu
 * @Date: 2020/12/14 16:13
 * @Description:
 */
public interface GradeService {

    /**
     * 分页
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<Grade> listPage(int start, int length);

    /**
     * 保存等级信息
     *
     * @param grade
     */
    void save(Grade grade);

    /**
     * 根据 ID 查询等级
     *
     * @param id
     * @return
     */
    Grade getById(Integer id);

    /**
     * 根据等级查询
     * @param currGrade
     */
    Grade getByGrade(Integer currGrade);
}

