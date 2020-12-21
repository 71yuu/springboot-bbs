package com.bbs.controller.admin;

import com.bbs.domain.Cat;
import com.bbs.domain.Notice;
import com.bbs.dto.PageInfo;
import com.bbs.service.CatService;
import com.bbs.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 通知管理
 */
@Controller
@RequestMapping("admin/notice")
public class AdminNoticeController {

    @Autowired
    private NoticeService noticeService;


    /**
     * 通知列表
     *
     * @return
     */
    @GetMapping("toList")
    public String toList() {
       return "admin/notice/list";
    }

    /**
     * 通知数据
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<Notice>  list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<Notice> pageInfo = noticeService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 通知表单页
     *
     * @return
     */
    @GetMapping("toNoticeForm")
    public String toNoticeForm(Integer id, Model model) {
        Notice notice = new Notice();
        if (!StringUtils.isEmpty(id)) {
            notice = noticeService.getById(id);
        }
        model.addAttribute("notice", notice);
        return "admin/notice/form";
    }

    /**
     * 保存通知信息
     *
     * @param cat
     * @return
     */
    @PostMapping("save")
    public String save(Notice notice) {
        noticeService.save(notice);
        return "redirect:/admin/notice/toList";
    }

    /**
     * 删除通知
     *
     * @param id
     * @return
     */
    @GetMapping("delete")
    public String delete(Integer id) {
        noticeService.deleteById(id);
        return "redirect:/admin/notice/toList";
    }
}
