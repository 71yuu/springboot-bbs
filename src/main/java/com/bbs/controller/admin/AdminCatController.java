package com.bbs.controller.admin;

import com.bbs.constant.IntegralRuleConstant;
import com.bbs.domain.Cat;
import com.bbs.domain.Grade;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;
import com.bbs.service.CatService;
import com.bbs.service.QuestionService;
import com.bbs.service.UserService;
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
 * 问题管理
 */
@Controller
@RequestMapping("admin/cat")
public class AdminCatController {

    @Autowired
    private CatService catService;


    /**
     * 版块列表
     *
     * @return
     */
    @GetMapping("toList")
    public String toList() {
       return "admin/cat/list";
    }

    /**
     * 版块
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<Cat>  list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<Cat> pageInfo = catService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 版块表单页
     *
     * @return
     */
    @GetMapping("toCatForm")
    public String toGradeForm(Integer id, Model model) {
        Cat cat = new Cat();
        if (!StringUtils.isEmpty(id)) {
            cat = catService.getById(id);
        }
        model.addAttribute("cat", cat);
        return "admin/cat/form";
    }

    /**
     * 保存版块信息
     *
     * @param cat
     * @return
     */
    @PostMapping("save")
    public String save(Cat cat) {
        catService.save(cat);
        return "redirect:/admin/cat/toList";
    }

    /**
     * 删除问题
     *
     * @param id
     * @return
     */
    @GetMapping("delete")
    public String delete(Integer id) {
        catService.deleteById(id);
        return "redirect:/admin/cat/toList";
    }
}
