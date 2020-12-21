package com.bbs.controller.front;


import com.bbs.domain.Cat;
import com.bbs.domain.Like;
import com.bbs.domain.Question;
import com.bbs.domain.User;
import com.bbs.dto.CommentVO;
import com.bbs.dto.QuestionVO;
import com.bbs.enums.CommonConstant;
import com.bbs.mapper.QuestionMapper;
import com.bbs.service.CatService;
import com.bbs.service.CommentService;
import com.bbs.service.LikeService;
import com.bbs.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 问题 Controller
 */
@Controller
@RequestMapping("question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private LikeService likeService;

    @Autowired
    private CommentService commentService;

    @Autowired
    private CatService catService;

    /**
     * 跳转到问题发布页
     *
     * @return
     */
    @GetMapping("/publish")
    public String toPublish(Model model) {
        // 查询所有分类
        List<Cat> cats = catService.getAll();
        model.addAttribute("cats", cats);
       return "publish";
    }

    /**
     * 发布问题
     *
     * @param question
     * @return
     */
    @PostMapping("/publish")
    public String publish(Question question, Model model, HttpSession session) {
       // 参数校验
       if (StringUtils.isEmpty(question.getTitle())) {
           model.addAttribute("error", "标题不能为空");
           return "publish";
       }
       if (StringUtils.isEmpty(question.getContent())) {
           model.addAttribute("error", "内容不能为空");
       }
       if (StringUtils.isEmpty(question.getCatId())) {
           model.addAttribute("error", "所选分类不能为空");
       }

       // 获取当前登录用户
       User user = (User) session.getAttribute("user");

        // 如果 ID 不为 null，则为编辑
        if (question.getId() != null) {
            question.setState(0);
            questionService.edit(question);
            return "redirect:/";
        }

        // ID 为 null，为新增
        else {
            // 设置为待审核状态
            question.setState(0);
            question.setCreator(user.getId());
            question.setViewCount(0);
            question.setDelFlag(CommonConstant.DEL_FLAG_NORMAL);
            questionService.save(question);
        }


       return "redirect:/";
    }

    /**
     * 查看详细问题
     *
     * @param id
     * @param model
     * @param request
     * @return
     */
    @GetMapping("/{id}")
    public String question(@PathVariable(name = "id") Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");

        // 查询问题
        QuestionVO questionVO = questionService.getById(id);

        // 如果未找到
        if (questionVO == null) {
            model.addAttribute("message", "未找到该文章或已被删除！");
            return "error";
        }

        // 查询当前用户是否点赞该文章
        if (questionVO != null && user != null) {
            Like like = likeService.getByUserIdAndQuestionId(user.getId(), questionVO.getId());
            if (like != null) {
                questionVO.setLiked(true);
            } else {
                questionVO.setLiked(false);
            }
        }

        // 查询问题的评论
        List<CommentVO> comments = commentService.getByQuestion(questionVO, user);

        // 浏览量 +1
        questionService.browse(questionVO.getId());

        questionVO.setViewCount(questionVO.getViewCount() + 1);
        model.addAttribute("question", questionVO);
        model.addAttribute("comments", comments);


        return "question";
    }

    /**
     * 删除问题
     *
     * @param questionId
     * @return
     */
    @GetMapping("/delete/{questionId}")
    public String deleteQuestion(@PathVariable("questionId") Integer questionId, Model model) {
        questionService.deleteById(questionId);
        model.addAttribute("redirect", "/");
        model.addAttribute("message", "删除成功");
        return "message";
    }

    /**
     * 编辑问题
     *
     * @param questionId
     * @return
     */
    @GetMapping("/edit/{questionId}")
    public String editQuestion(@PathVariable("questionId") Integer questionId, Model model) {
        QuestionVO questionVO = questionService.getById(questionId);
        model.addAttribute("question", questionVO);

        // 查询所有版块
        List<Cat> cats = catService.getAll();
        model.addAttribute("cats", cats);
        return "publish";
    }
}
