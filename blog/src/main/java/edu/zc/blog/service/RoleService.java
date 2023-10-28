package edu.zc.blog.service;

import edu.zc.blog.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zc.blog.entity.Vo.RoleVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author keeplooking
 * @since 2022-02-25
 */
public interface RoleService extends IService<Role> {

    /**
     * 新增角色时判断该角色是否存在，
     * @param roleVo 增加的角色信息
     * @return 如果存在，则返回该角色Id，如果不存在，则新建角色，并返回新角色的ID
     */
    String saveRole(RoleVo roleVo);


}
