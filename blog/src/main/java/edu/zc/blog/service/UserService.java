package edu.zc.blog.service;

import edu.zc.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;
import edu.zc.blog.entity.Vo.LoginVo;
import edu.zc.blog.entity.Vo.RegisterVo;
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
public interface UserService extends IService<User> {

    /**
     * 登录
     * @param loginVo 用户信息
     * @return 返回用户专属token
     */
    Map<String,Object> login(LoginVo loginVo);

    /**
     * 注册
     * @param registerVo 新用户信息
     */
    Boolean register(RegisterVo registerVo);

    /**
     *
     * @param current 当前页数
     * @param limit 每页记录数
     * @param request 前端的请求
     * @return map 返回的数据
     */
    Map<String, Object> getAllUser(long current, long limit, HttpServletRequest request);
}
