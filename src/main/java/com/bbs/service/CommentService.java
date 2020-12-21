package com.bbs.service;

import com.bbs.domain.Comment;
import com.bbs.domain.User;
import com.bbs.dto.CommentVO;
import com.bbs.dto.PageInfo;
import com.bbs.dto.QuestionVO;

import java.util.List;

public interface CommentService{

    /**
     * 获取问题的评论
     *
     * @param questionVO
     * @param i
     * @param user
     * @return
     */
    List<CommentVO> getByQuestion(QuestionVO questionVO, User user);

    /**
     * 保存评论
     *
     * @param comment
     */
    void save(Comment comment);

    /**
     * 查询评论的子评论
     *
     * @param commentId
     * @return
     */
    List<CommentVO> getByCommentId(Integer commentId);

    /**
     * 根据评论 ID 查询评论
     *
     * @param parentId
     * @return
     */
    Comment getById(Integer parentId);

    /**
     * 获取评论的分页
     *
     * @param start
     * @param length
     * @return
     */
    PageInfo<CommentVO> listPage(int start, int length);

    /**
     * 删除评论
     *
     * @param id
     */
    void deleteById(Integer id);
}
