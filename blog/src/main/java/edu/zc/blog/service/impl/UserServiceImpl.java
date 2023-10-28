package edu.zc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zc.blog.entity.User;
import edu.zc.blog.entity.Vo.*;
import edu.zc.blog.mapper.RoleMapper;
import edu.zc.blog.mapper.UserMapper;
import edu.zc.blog.service.CategoryService;
import edu.zc.blog.service.RoleService;
import edu.zc.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zc.globalException.exception.DiyException;
import edu.zc.utils.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService{

    @Resource
    private RoleMapper roleMapper;

    @Resource
    private UserMapper userMapper;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RoleService roleService;

    @Override
    public Map<String,Object> login(LoginVo loginVo) {
        String username = loginVo.getUsername();
        String password = loginVo.getPassword();
//        判断是否为空

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            throw new DiyException(-1, "输入信息不全，请完整输入！");
        }

//        判断用户名是否符合规则


//        判断是否有该用户
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username);
        User user = baseMapper.selectOne(wrapper);
        if(user == null){
            throw new DiyException(-1, "该用户不存在，请重新输入！");
        }

//        判断密码是否正确
        if(!password.equals(user.getPassword())){
           throw new DiyException(-1,"密码错误，请重新输入密码");
        }

        Map<String,Object> map = new HashMap<>();
        String roleName = roleMapper.getRoleName(username);
        String token = JWTUtils.getJwtToken(roleName, user.getId(), user.getUsername());

        map.put("uuid","admin-uuid");
        map.put("token",token);
        map.put("rolename",roleName);
//        登陆成功，返回token
        return map;
    }

    @Override
    public Boolean register(RegisterVo registerVo) {
        String username = registerVo.getUsername();
        String password = registerVo.getPassword();
        String email = registerVo.getEmail();
//        判断是否为空
        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password) || StringUtils.isEmpty(email)) {
            throw new DiyException(-1, "输入信息不全，请完整输入！");
        }
//        判断是否存在该用户(应该在输入用户名时就校验，而不是点击“注册”后）
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        User user = baseMapper.selectOne(queryWrapper);
        if(user != null){
            throw new DiyException(-1,"已有同名用户注册，请更换用户名!");
        }
//        判断邮箱是否符合格式

//        判断是否存在该邮箱


//        入库
        User user2 = new User();
        BeanUtils.copyProperties(registerVo,user2);

        //        插入角色(默认普通用户)
//        角色类型简单直接插入名字就可以，而不是id，用户类型多的话，插入的可以是roleId
//        目前策略：改动最少的代码
//        既然插入roleId要是固定的一个Id
//        那么，就在此处新建一个角色，如果该角色名称存在，则不新建角色

        RoleVo roleVo = new RoleVo();
        roleVo.setRole("user");
        String roleId = roleService.saveRole(roleVo);
        if(roleId.equals("新建角色失败")){
            return false;
        }
        user2.setRoleId(roleId);
        int insert = baseMapper.insert(user2);

        CategoryQuery categoryQuery = new CategoryQuery();
        categoryQuery.setCategoryName("默认分类");
        categoryQuery.setUserName(username);

        String userId = userMapper.selectUserId(username);

        if(insert < 0){
            return false;
        }
        return categoryService.saveDefaultCategoryQuery(categoryQuery, userId);
    }

    @Override
    public Map<String, Object> getAllUser(long current,
                                          long limit,
                                          HttpServletRequest request){
        //        设定分页条件与对象
        Page<UserQuery> ipage = new Page<>(current, limit);
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();

//        判断用户为管理员还是普通用户
//            获取token中的userid和role
        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }
        String roleName = JWTUtils.getRoles(request);

//            如果角色为管理员
        if(roleName.equals("admin")){
            ipage.setRecords(userMapper.selectAllUserInfo());
            ipage.setTotal(userMapper.selectCount(queryWrapper));

            Map<String,Object> map = new HashMap<>();
            map.put("list",ipage);
            return map;
        }
//            如果角色为普通用户
        UserQuery userQuery = userMapper.selectUserInfo(userId);
        Map<String,Object> map = new HashMap<>();
        map.put("personalInfo",userQuery);

        return map;
    }


}
