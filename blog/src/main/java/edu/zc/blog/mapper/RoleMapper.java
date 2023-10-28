package edu.zc.blog.mapper;

import edu.zc.blog.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;


/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author keeplooking
 * @since 2022-02-25
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取角色名
     * @param username
     * @return
     */
    @Select("SELECT r.role " +
            "FROM role r,user u " +
            "WHERE r.id = u.role_id and u.username = #{username}")
    String getRoleName(String username);
}
