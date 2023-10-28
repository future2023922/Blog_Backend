package edu.zc.blog.service;

import edu.zc.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zc.blog.entity.Vo.BlogQuery;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
public interface BlogService extends IService<Blog> {

    /**
     * 获取博客信息
     * @param current 当前请求页数
     * @param limit 请求每页限制记录数
     * @param blogQuery 博客信息
     * @return 返回map
     */
    Map<String, Object> getAllOfUserBlog(long current, long limit, BlogQuery blogQuery,HttpServletRequest request);

    /**
     * 保存用户博客
     * @param blogQuery 博客信息
     * @param request 前端发来的请求
     * @return 返回布尔值
     */
    boolean saveBlog(BlogQuery blogQuery, HttpServletRequest request);

    /**
     * 获取用户谋篇具体博客信息
     * @param blogId 博客id
     * @param request 前端发来的请求
     * @return 返回布尔值
     */
    Map<String,Object> getOneBlog(String blogId, HttpServletRequest request);
}
