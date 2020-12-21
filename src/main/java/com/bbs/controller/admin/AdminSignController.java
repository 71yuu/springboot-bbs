package com.bbs.controller.admin;

import com.bbs.domain.Notice;
import com.bbs.domain.Sign;
import com.bbs.dto.PageInfo;
import com.bbs.dto.SignVO;
import com.bbs.service.NoticeService;
import com.bbs.service.SignService;
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
 * 签到管理
 */
@Controller
@RequestMapping("admin/sign")
public class AdminSignController {

    @Autowired
    private SignService signService;


    /**
     * 签到列表
     *
     * @return
     */
    @GetMapping("toList")
    public String toList() {
       return "admin/sign/list";
    }

    /**
     * 签到数据
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<SignVO>  list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<SignVO> pageInfo = signService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }
}
