package com.bbs.controller.admin;

import com.bbs.domain.User;
import com.bbs.dto.CommentVO;
import com.bbs.dto.PageInfo;
import com.bbs.service.CommentService;
import com.bbs.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户管理
 */
@Controller
@RequestMapping("admin/user")
public class AdminUserController {

    @Autowired
    private UserService userService;

    /**
     * 用户列表
     *
     * @return
     */
    @GetMapping("toList")
    public String toList() {
        return "admin/user/list";
    }

    /**
     * 用户列表
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<User> list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<User> pageInfo = userService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @GetMapping("delete")
    public String delete(Integer id) {
        userService.deleteById(id);
        return "redirect:/admin/user/toList";
    }
}
