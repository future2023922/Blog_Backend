package edu.zc.blog.service;

import edu.zc.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
public interface CommentService extends IService<Comment> {
    /**
     * 保存评论
     * @param comment 评论内容
     * @param request 前台请求
     * @return 是否插入成功
     */
    boolean saveComment(Comment comment, HttpServletRequest request);

    /**
     * 获取评论列表
     * @param blogId 确定是那篇博客的评论列表
     * @param request 前台请求
     * @return 返回评论列表
     */
    List<Comment> commentList(String blogId,HttpServletRequest request);

    /**
     * 删除评论及其子评论
     * @param commentId 删除的评论id
     * @param request 前台请求
     * @return 是否删除成功
     */
    boolean deleteComment(String commentId,HttpServletRequest request);
}
