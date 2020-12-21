package com.bbs.service;

import com.bbs.domain.Notice;
import com.bbs.domain.Sign;
import com.bbs.dto.PageInfo;
import com.bbs.dto.SignVO;

import java.util.List;

public interface SignService {


    /**
     * 判断今日是否已经签到
     *
     * @param userId
     * @return
     */
    Sign todaySign(Integer userId);

    /**
     * 查询本月签到天数
     *
     * @param id
     * @return
     */
    List<Integer> monthSign(Integer id);

    /**
     * 签到
     *
     * @param id
     * @return
     */
    Sign sign(Integer id);

    /**
     * 签到列表
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<SignVO> listPage(int start, int length);
}

