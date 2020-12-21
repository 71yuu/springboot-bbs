package com.bbs.service.impl;

import com.bbs.domain.Cat;
import com.bbs.domain.Comment;
import com.bbs.domain.Grade;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;
import com.bbs.enums.CommonConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bbs.mapper.CatMapper;
import com.bbs.service.CatService;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CatServiceImpl implements CatService{

    @Resource
    private CatMapper catMapper;

    @Override
    public List<Cat> getAll() {
        Example catExample = new Example(Cat.class);
        catExample.createCriteria().andEqualTo("delFlag", CommonConstant.DEL_FLAG_NORMAL);
        List<Cat> cats = catMapper.selectByExample(catExample);
        return cats;
    }

    @Override
    public PageInfo<Cat> listPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 分页查询
        com.github.pagehelper.PageInfo<Cat> catPageInfo = new com.github.pagehelper.PageInfo<>(catMapper.selectAll());
        List<Cat> cats = catPageInfo.getList();

        // 创建分页对象
        PageInfo<Cat> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)catPageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)catPageInfo.getTotal());
        pageInfo.setData(cats);
        return pageInfo;
    }

    @Override
    public Cat getById(Integer id) {
        return catMapper.selectByPrimaryKey(id);
    }

    @Override
    public void save(Cat cat) {
        // 新增
        if (StringUtils.isEmpty(cat.getId())) {
            cat.setDelFlag(CommonConstant.DEL_FLAG_NORMAL);
            catMapper.insert(cat);
        }

        // 编辑
        else {
            catMapper.updateByPrimaryKeySelective(cat);
        }
    }

    @Override
    public void deleteById(Integer id) {
        Cat cat = catMapper.selectByPrimaryKey(id);
        cat.setDelFlag(CommonConstant.DEL_FLAG_DEL);
        catMapper.updateByPrimaryKeySelective(cat);
    }
}
