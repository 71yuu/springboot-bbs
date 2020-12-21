package com.bbs.service.impl;

import com.bbs.domain.Feedback;
import com.bbs.domain.Grade;
import com.bbs.domain.User;
import com.bbs.dto.FeedbackVO;
import com.bbs.dto.PageInfo;
import com.bbs.enums.CommonConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bbs.mapper.GradeMapper;
import com.bbs.service.GradeService;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

@Service
public class GradeServiceImpl implements GradeService{

    @Resource
    private GradeMapper gradeMapper;

    @Override
    public PageInfo<Grade> listPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 分页查询
        com.github.pagehelper.PageInfo<Grade> gradePageInfo = new com.github.pagehelper.PageInfo<>(gradeMapper.selectAll());
        List<Grade> grades = gradePageInfo.getList();

        // 创建分页对象
        PageInfo<Grade> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)gradePageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)gradePageInfo.getTotal());
        pageInfo.setData(grades);
        return pageInfo;
    }

    @Override
    public void save(Grade grade) {
        // 新增
        if (StringUtils.isEmpty(grade.getId())) {
            gradeMapper.insert(grade);
        }

        // 编辑
        else {
            gradeMapper.updateByPrimaryKeySelective(grade);
        }
    }

    @Override
    public Grade getById(Integer id) {
        return gradeMapper.selectByPrimaryKey(id);
    }

    @Override
    public Grade getByGrade(Integer currGrade) {
        Example example = new Example(Grade.class);
        example.createCriteria().andEqualTo("grade", currGrade);
        Grade grade = gradeMapper.selectOneByExample(example);
        return grade;
    }
}
