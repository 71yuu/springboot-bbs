package com.bbs.controller.front;

import com.bbs.domain.Like;
import com.bbs.domain.User;
import com.bbs.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Like Controller
 */
@Controller
public class LikeController {

    @Autowired
    private LikeService likeService;

    /**
     * 点赞
     *
     * @param like
     * @return
     */
    @PostMapping("like")
    @ResponseBody
    public Object like(@RequestBody Like like, HttpSession session) {
        // 获取当前登录用户
        User user = (User) session.getAttribute("user");

        // 查询原来的状态
        like.setUserId(user.getId());
        Integer state = likeService.like(like);
        return state;
    }
}
