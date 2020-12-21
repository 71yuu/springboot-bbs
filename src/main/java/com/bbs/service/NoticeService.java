package com.bbs.service;

import com.bbs.domain.Notice;
import com.bbs.dto.PageInfo;

import java.util.List;

public interface NoticeService{


    /**
     * 查询公告
     *
     * @return
     */
    Notice getNotice();

    /**
     * 分页查询
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<Notice> listPage(int start, int length);

    /**
     * 根据 ID 查询
     * @param id
     * @return
     */
    Notice getById(Integer id);

    /**
     * 保存
     *
     * @param notice
     */
    void save(Notice notice);

    /**
     * 删除
     *
     * @param id
     */
    void deleteById(Integer id);
}
