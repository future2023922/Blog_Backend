package edu.zc.blog.mapper;

import edu.zc.blog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zc.blog.entity.Vo.BlogQuery;
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
public interface BlogMapper extends BaseMapper<Blog> {
    /**
     * 获取所有用户的博客列表(多表查询)
     * @return List
     */
    @Select("SELECT b.id, b.category_id, b.title, b.author_id, b.update_times,b.create_time,b.content,b.version " +
            "FROM blog b,category c,user u " +
            "WHERE b.category_id = c.category_id and b.author_id = u.id")
    List<Blog> getAllBlogs();


    /**
     * 获取对应用户的博客列表(多表查询)
     * @return List
     */
    @Select("SELECT b.id, b.category_id, b.title, b.author_id, b.update_times,b.create_time,b.content,b.version " +
            "FROM blog b,category c,user u " +
            "WHERE b.category_id = c.category_id and b.author_id = u.id and u.id = #{userId} order by create_time Desc")
    List<Blog> getMyAllBlogs(String userId);

    /**
     * 获取用户某篇博客的具体信息
     * @return List
     */
    @Select("SELECT b.id, c.category_name, b.title, u.username, b.update_times,b.create_time,b.content,b.copyright " +
            "FROM blog b,category c,user u " +
            "WHERE b.category_id = c.category_id and b.author_id = u.id and u.id = #{userId} and b.id = #{blogId} order by create_time Desc")
    BlogQuery getBlogInfo(String userId,String blogId);
}
