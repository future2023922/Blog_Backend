package edu.zc.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zc.blog.entity.User;
import edu.zc.blog.entity.Vo.LoginVo;
import edu.zc.blog.entity.Vo.RegisterVo;
import edu.zc.blog.entity.Vo.RoleVo;
import edu.zc.blog.entity.Vo.UserQuery;
import edu.zc.blog.service.CategoryService;
import edu.zc.blog.service.RoleService;
import edu.zc.blog.service.UserService;

import edu.zc.globalException.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
@Api(tags = "用户模块")
@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;

    @Autowired
    CategoryService categoryService;

    @ApiOperation(value="登录")
    @PostMapping("/login")
    public Results login(@ApiParam(name="user",value="新用户信息",required = true)@RequestBody LoginVo loginVo){

        Map<String, Object> map = userService.login(loginVo);
        return Results.success().data(map);
    }

    @ApiOperation(value="添加新用户(注册)")
    @PostMapping("/addUser")
    public Results addUser(@ApiParam(name="user",value="新用户信息",required = true) @RequestBody RegisterVo registerVo, HttpServletRequest request){
//TODO:添加短信/邮箱验证
        Boolean register = userService.register(registerVo);
        if(register){
            return Results.success();
        }
        return Results.fail().msg("用户未注册成功");
    }

    @ApiOperation(value="条件查询用户信息")
    @PostMapping("/getUser")
    public Results getUser(@ApiParam(name="UserQuery",value="用户对象") @RequestBody UserQuery userQuery){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        String username = userQuery.getUsername();
        String email = userQuery.getEmail();
        String roleName = userQuery.getRole();

        if(email != null){
            queryWrapper.like("email",email);
        }
        if(username != null){
            queryWrapper.like("username",username);
        }
        if(roleName != null){
            queryWrapper.like("role",roleName);
        }

        List<User> list = userService.list(queryWrapper);
        if(list.isEmpty())
            return Results.fail().msg("查无此用户");
        return Results.success().data("list",list);
    }

    @ApiOperation(value="分页获取所有用户信息")
    @GetMapping("/getAllUser/{current}/{limit}")
    public Results getAllUser(@ApiParam(name="current",value="当前页",required = true) @PathVariable long current,
                                 @ApiParam(name="limit",value="每页记录数",required = true) @PathVariable long limit,
                              HttpServletRequest request) {

        Map<String, Object> map = userService.getAllUser(current, limit, request);
        return Results.success().data(map);
    }

    @ApiOperation(value="修改用户信息")
    @PostMapping("/updateUser")
    public Results updateUser(@RequestBody User user){
        boolean update = userService.updateById(user);
        if(update)
            return Results.success();
        return Results.fail();
    }

    @ApiOperation(value="删除用户信息")
    @DeleteMapping("/updateUser/{userId}")
    public Results deleteUser(@ApiParam(value="userId",required = true)@PathVariable Long userId){
        boolean update = userService.removeById(userId);
        if(update)
            return Results.success();
        return Results.fail();
    }

    @ApiOperation(value = "增加用户角色")
    @PostMapping("/addrole")
    public Results addRole(@ApiParam(value="role",required = true)@RequestBody RoleVo roleVo){
        String save = roleService.saveRole(roleVo);
        if(!(save.equals("新建角色失败")))
            return Results.success();
        return Results.fail().msg(save);
    }


}

