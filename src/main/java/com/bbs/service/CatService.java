package com.bbs.service;

import com.bbs.domain.Cat;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;

import java.util.List;

public interface CatService{


    /**
     * 查询所有分类
     *
     * @return
     */
    List<Cat> getAll();

    /**
     * 分页列表
     * @param start
     * @param length
     * @return
     */
    PageInfo<Cat> listPage(int start, int length);

    /**
     * 根据 ID 查询
     * @param id
     * @return
     */
    Cat getById(Integer id);

    /**
     * 保存
     * @param cat
     */
    void save(Cat cat);

    /**
     * 根据 ID 查询
     * @param id
     */
    void deleteById(Integer id);
}
