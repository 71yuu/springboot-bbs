package com.bbs.controller.admin;

import com.bbs.domain.Grade;
import com.bbs.dto.CommentVO;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;
import com.bbs.service.GradeService;
import com.bbs.service.QuestionService;
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
 * 等级管理
 */
@Controller
@RequestMapping("admin/grade")
public class AdminGradeController {

    @Autowired
    private GradeService gradeService;

    /**
     * 等级列表
     *
     * @return
     */
    @GetMapping("toGradeList")
    public String toGradeList() {
        return "admin/grade/list";
    }

    /**
     * 等级列表
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<Grade>  list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<Grade> pageInfo = gradeService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 等级表单页
     *
     * @return
     */
    @GetMapping("toGradeForm")
    public String toGradeForm(Integer id, Model model) {
        Grade grade = new Grade();
        if (!StringUtils.isEmpty(id)) {
            grade = gradeService.getById(id);
        }
        model.addAttribute("grade", grade);
        return "admin/grade/form";
    }

    /**
     * 保存等级信息
     *
     * @param grade
     * @return
     */
    @PostMapping("save")
    public String save(Grade grade) {
        gradeService.save(grade);
        return "redirect:/admin/grade/toGradeList";
    }

}
