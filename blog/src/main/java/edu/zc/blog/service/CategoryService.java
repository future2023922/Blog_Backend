package edu.zc.blog.service;

import edu.zc.blog.entity.Category;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zc.blog.entity.Vo.CategoryQuery;

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
public interface CategoryService extends IService<Category> {

    /**
     * 新增分类
     * @param categoryQuery 分类信息
     * @return 返回布尔值
     */
    boolean saveCategoryQuery(CategoryQuery categoryQuery,HttpServletRequest request);


    /**
     * 新增用户的默认分类
     * @param categoryQuery 分类信息
     * @return 返回布尔值
     */
    boolean saveDefaultCategoryQuery(CategoryQuery categoryQuery,String userId);

    /**
     * 查询所有分类
     * @param request 当前用户请求
     * @return 返回分类信息
     */
    Map<String,Object> selectAllCategory(HttpServletRequest request);

    /**
     * 获取每个分类的博客数量
     * @param request 当前用户请求
     * @return 返回分类信息
     */
    Map<String,Object> selectCategoryAndBlogCount(HttpServletRequest request);

    /**
     * 删除分类及分类下的博客
     * @param categoryId 分类id
     * @return 是否删除成功
     */
    Boolean deleteByIdOfCategory(Long categoryId);
}
