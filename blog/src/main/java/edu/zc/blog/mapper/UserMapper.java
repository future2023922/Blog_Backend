package edu.zc.blog.mapper;

import edu.zc.blog.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import edu.zc.blog.entity.Vo.UserQuery;
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
public interface UserMapper extends BaseMapper<User> {

    @Select("SELECT u.id " +
            "FROM user u " +
            "WHERE u.username = #{username}")
    String selectUserId(String username);

    @Select("SELECT * " +
            "FROM user u " +
            "WHERE u.username = #{username}")
    User selectUser(String username);

    /**
     * 获取自己的用户信息
     * @param userId 用户id
     * @return 返回用户不敏感信息
     */
    @Select("SELECT role,username,email,create_time " +
            "FROM user u ,role r " +
            "WHERE u.id = #{userId} and r.id = u.role_id")
    UserQuery selectUserInfo(String userId);

    /**
     * 获取所有用户信息
     * @return 返回所有用户信息
     */
    @Select("SELECT role,username,email,create_time " +
            "FROM user u ,role r " +
            "WHERE r.id = u.role_id")
    List<UserQuery> selectAllUserInfo();
}
