package com.bbs.controller.admin;

import com.bbs.domain.Grade;
import com.bbs.domain.IntegralRule;
import com.bbs.dto.PageInfo;
import com.bbs.dto.SignVO;
import com.bbs.service.IntegralRuleService;
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
 * 积分规则管理
 */
@Controller
@RequestMapping("admin/integralRule")
public class AdminIntegralRuleController {

    @Autowired
    private IntegralRuleService integralRuleService;


    /**
     * 积分规则列表
     *
     * @return
     */
    @GetMapping("toList")
    public String toList() {
       return "admin/integralRule/list";
    }

    /**
     * 签到数据
     *
     * @return
     */
    @GetMapping("list")
    @ResponseBody
    public PageInfo<IntegralRule>  list(HttpServletRequest request) {
        String draw = request.getParameter("draw");
        int start = Integer.parseInt(request.getParameter("start"));
        int length = Integer.parseInt(request.getParameter("length"));
        // 处理分页开始条数问题
        if (start > 1) {
            start = start / length + 1;
        }

        PageInfo<IntegralRule> pageInfo = integralRuleService.listPage(start, length);
        pageInfo.setDraw(draw == null ? 0 : Integer.parseInt(draw));
        return pageInfo;
    }

    /**
     * 等级表单页
     *
     * @return
     */
    @GetMapping("toIntegralRuleForm")
    public String toIntegralRuleForm(Integer id, Model model) {
        IntegralRule integralRule = new IntegralRule();
        if (!StringUtils.isEmpty(id)) {
            integralRule = integralRuleService.getById(id);
        }
        model.addAttribute("integralRule", integralRule);
        return "admin/integralRule/form";
    }

    /**
     * 保存等级信息
     *
     * @param grade
     * @return
     */
    @PostMapping("save")
    public String save(IntegralRule integralRule) {
        integralRuleService.save(integralRule);
        return "redirect:/admin/integralRule/toList";
    }
}
