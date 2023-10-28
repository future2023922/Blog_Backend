package edu.zc.blog.mapper;

import edu.zc.blog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface CommentMapper extends BaseMapper<Comment> {
    /**
     * 获取根评论
     * @param blogId 某篇博客id
     * @return 返回跟评论列表
     */
    @Select("select c.id,c.content,c.user_id,u.username,c.create_time,c.parent_comment_id "
            + "from comment c,user u "
            + "where c.user_id = u.id and parent_comment_id = -1 and blog_id = #{blogId} and c.is_deleted = 0")
    List<Comment> selectRootList(String blogId);

    /**
     * 获取子评论
     * @param blogId 某篇博客id
     * @return 返回子评论列表
     */
    @Select("select c.id,c.content,c.user_id,u.username,c.create_time,parent_comment_id "
            + "from comment c,user u "
            + "where c.user_id = u.id and parent_comment_id != -1 and blog_id = #{blogId} and c.is_deleted = 0")
    List<Comment> selectChildList(String blogId);
}
