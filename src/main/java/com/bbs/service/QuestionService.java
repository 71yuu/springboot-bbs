package com.bbs.service;

import com.bbs.domain.Question;
import com.bbs.dto.PageInfo;
import com.bbs.dto.PageResult;
import com.bbs.dto.QuestionVO;

import java.util.List;
import java.util.Map;

public interface QuestionService{

    /**
     * 分页查询问题
     *
     * @param search
     * @param page
     * @param size
     * @return
     */
    PageResult<QuestionVO> page(String search, Integer catId, Integer page, Integer size);

    /**
     * 保存问题
     *
     * @param question
     */
    void save(Question question);

    /**
     * 我的问题
     *
     * @param id
     * @param page
     * @param size
     * @return
     */
    PageResult<QuestionVO> listByUser(Integer id, Integer page, Integer size);

    /**
     * 根据 ID 查询问题详情
     *
     * @param id
     * @return
     */
    QuestionVO getById(Integer id);

    /**
     * 删除问题
     *
     * @param questionId
     */
    void deleteById(Integer questionId);

    /**
     * 浏览问题
     *
     * @param id
     */
    void browse(Integer id);

    /**
     * 后台 - 待审核问题分页查询
     *
     * @return
     */
    PageInfo<QuestionVO> auditListPage(Integer page, Integer pageSize);

    /**
     * 后台 - 问题列表
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<QuestionVO> listPage(int start, int length);

    /**
     * 通过审核
     *
     * @param id
     */
    void yesAudit(Integer id);

    /**
     * 不通过审核
     *
     * @param id
     */
    void noAudit(String id);

    /**
     * 编辑问题
     *
     * @param question
     */
    void edit(Question question);

    /**
     * 热门问题榜单
     *
     * @return
     */
    List<QuestionVO> getHot();
}
