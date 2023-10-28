package edu.zc.blog.mapper;

import edu.zc.blog.entity.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zc.blog.entity.Vo.BlogQuery;
import edu.zc.blog.entity.Vo.CategoryQuery;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
public interface CategoryMapper extends BaseMapper<Category> {

    /**
     * 获取所有用户的所有分类信息
     * @return 返回所有用户的分类信息
     */
    @Select("SELECT c.category_id,c.category_name,u.username " +
            "FROM category c,user u " +
            "WHERE c.author_id = u.id")
    List<CategoryQuery> getAllCategory();

    /**
     * 获取某用户的所有分类信息
     * @return 返回某用户的分类信息
     */
    @Select("SELECT c.category_id,c.category_name,u.username " +
            "FROM category c,user u " +
            "WHERE c.author_id = u.id and u.id = #{id}")
    List<CategoryQuery> getMyCategory(String id);

    /**
     * 根据分类名查询分类id
     * @return 获取我的当前分类的id
     */
    @Select("SELECT c.category_id " +
            "FROM category c,user u " +
            "WHERE c.author_id = u.id and u.id = #{userId} and category_name = #{categoryName}")
    String getMyCategoryId(String userId,String categoryName);

    /**
     * 获取每个分类的博客数量
     * @return 返回每个分类的博客数量
     */
    @Select("SELECT DISTINCT c.category_id, c.category_name,u.username, COUNT(b.category_id) blog_count " +
            "FROM category c LEFT OUTER JOIN blog b " +
            "ON b.category_id = c.category_id " +
            "LEFT OUTER JOIN user u " +
            "on c.author_id = u.id " +
            "GROUP BY c.category_id " +
            "ORDER BY COUNT(b.category_id) DESC")
    List<CategoryQuery> getCategoryCount();

    /**
     * 获取某用户的每个分类的博客数量
     * @return 返回某用户的每个分类的博客数量
     */
    @Select("SELECT DISTINCT c.category_id, c.category_name,u.username, COUNT(b.category_id) blog_count " +
            "FROM category c LEFT OUTER JOIN blog b " +
            "ON b.category_id = c.category_id " +
            "LEFT OUTER JOIN user u " +
            "on c.author_id = u.id " +
            "where c.author_id = #{userId} " +
            "GROUP BY c.category_id " +
            "ORDER BY COUNT(b.category_id) DESC")
    List<CategoryQuery> getMyCategoryCount(String userId);

    /**
     * 找到某个分类信息
     * @param userId 用户id
     * @param categoryId 分类id
     * @return 返回某个分类信息
     */
    @Select("SELECT c.category_id,c.category_name,u.username " +
            "FROM category c,user u " +
            "WHERE c.author_id = u.id and u.id = #{id} and c.category_id = #{categoryId}")
    BlogQuery getCategoryInfo(String userId, String categoryId);
}
