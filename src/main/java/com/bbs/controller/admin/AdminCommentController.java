package com.bbs.controller.admin;

import com.bbs.domain.Comment;
import com.bbs.dto.CommentVO;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;
import com.bbs.service.CommentService;
import com.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 评论管理
 */
@Controller
@RequestMapping("admin/comment")
public class AdminCommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 评论列表
     *
     * @return
     */
    @GetMapping("toList")
    public String toList() {
        return "admin/comment/list";
    }

    /**
     * 评论列表
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<CommentVO>  list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<CommentVO> pageInfo = commentService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @GetMapping("delete")
    public String delete(Integer id) {
        commentService.deleteById(id);
        return "redirect:/admin/comment/toList";
    }
}
