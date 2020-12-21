package com.bbs.service.impl;

import com.bbs.domain.Comment;
import com.bbs.domain.Grade;
import com.bbs.domain.Notice;
import com.bbs.dto.PageInfo;
import com.bbs.enums.CommonConstant;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import com.bbs.mapper.NoticeMapper;
import com.bbs.service.NoticeService;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.entity.Example;

import java.util.Date;
import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService{

    @Resource
    private NoticeMapper noticeMapper;

    @Override
    public Notice getNotice() {
        Example example = new Example(Notice.class);
        example.orderBy("noticeTime").desc();
        List<Notice> notices = noticeMapper.selectByExample(example);
        if (notices.size() > 0) {
            return notices.get(0);
        }
        return null;
    }

    @Override
    public PageInfo<Notice> listPage(int start, int length) {
        // 设置分页查询条件
        PageHelper.startPage(start, length);

        // 分页查询
        com.github.pagehelper.PageInfo<Notice> gradePageInfo = new com.github.pagehelper.PageInfo<>(noticeMapper.selectAll());
        List<Notice> notices = gradePageInfo.getList();

        // 创建分页对象
        PageInfo<Notice> pageInfo = new PageInfo<>();
        pageInfo.setRecordsTotal((int)gradePageInfo.getTotal());
        pageInfo.setRecordsFiltered((int)gradePageInfo.getTotal());
        pageInfo.setData(notices);
        return pageInfo;
    }

    @Override
    public void save(Notice notice) {
        // 新增
        if (StringUtils.isEmpty(notice.getId())) {
            notice.setNoticeTime(new Date());
            notice.setDelFlag(CommonConstant.DEL_FLAG_NORMAL);
            noticeMapper.insert(notice);
        }

        // 编辑
        else {
            noticeMapper.updateByPrimaryKeySelective(notice);
        }
    }

    @Override
    public void deleteById(Integer id) {
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        notice.setDelFlag(CommonConstant.DEL_FLAG_DEL);
        noticeMapper.updateByPrimaryKeySelective(notice);
    }

    @Override
    public Notice getById(Integer id) {
        return noticeMapper.selectByPrimaryKey(id);
    }

}
