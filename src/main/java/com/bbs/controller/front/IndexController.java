package com.bbs.controller.front;

import com.bbs.domain.Cat;
import com.bbs.domain.Notice;
import com.bbs.dto.PageResult;
import com.bbs.dto.QuestionVO;
import com.bbs.service.CatService;
import com.bbs.service.NoticeService;
import com.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * 前台首页 Controller
 */
@Controller
public class IndexController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private CatService catService;


    /**
     * 分页查询问题
     *
     * @param page 第几页
     * @param size 每页的大小
     * @param search 搜索条件
     * @return
     */
    @GetMapping("/")
    public String index(@RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "10") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        Integer catId,
                        Model model) {
        // 调用 Service 层分页查询
        PageResult<QuestionVO> questionPage = questionService.page(search, catId, page, size);
        model.addAttribute("questionPage", questionPage);
        model.addAttribute("search", search);

        // 本周热门问题 5 条（评论最多）
        List<QuestionVO> hotQuestions = questionService.getHot();
        model.addAttribute("hotQuestions", hotQuestions);

        // 查询所有版块
        List<Cat> cats = catService.getAll();
        model.addAttribute("cats", cats);
        model.addAttribute("catId", catId);

        // 公告栏
        Notice notice = noticeService.getNotice();
        model.addAttribute("notice", notice);

        return "index";
    }
}
