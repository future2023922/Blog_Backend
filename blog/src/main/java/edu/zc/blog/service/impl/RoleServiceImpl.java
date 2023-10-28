package edu.zc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zc.blog.entity.Role;
import edu.zc.blog.entity.Vo.RoleVo;
import edu.zc.blog.mapper.RoleMapper;
import edu.zc.blog.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author keeplooking
 * @since 2022-02-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {


    @Override
    public String saveRole(RoleVo roleVo) {

//        判断是否存在该角色
        String roleName = roleVo.getRole();
        QueryWrapper<Role> roleQueryWrapper = new QueryWrapper<>();
        roleQueryWrapper.eq("role",roleName);
        Role role = baseMapper.selectOne(roleQueryWrapper);
        if(role != null){
            return role.getId();
        }
//        如果不存在,则插入该角色
        Role role2 = new Role();
        BeanUtils.copyProperties(roleVo,role2);
        int insert = baseMapper.insert(role2);
        if(insert > 0){
            return role2.getId();
        }
        return "新建角色失败";
    }
}
