package edu.zc.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author: keeplooking
 * @since: 2022/02/25 - 17:55
 */
public class JWTUtils {
//    设置token过期时间为24小时
    public static final long EXPIRE = 1000 * 60 * 60 * 24;
//    签名哈希的密钥
    public static final String APP_SECRET = "ukc8BDbRigUDaY6pZFfWus2jZWLPHO";

    /**
     * 根据用户id和昵称生成token
     */
    public static String getJwtToken(String roles, String userId, String username){

        String JwtToken = Jwts.builder()
                //设置token头信息
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "HS256")

                .setSubject("blog")

                //修改过期时间
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))

                //设置token主题部分,存储用户信息
                .claim("username", username)
                .claim("userId", userId)
                .claim("roles", roles)

                .signWith(SignatureAlgorithm.HS256, APP_SECRET)
                .compact();

        return JwtToken;
    }

    /**
     * 判断token是否存在与有效
     */
    public static boolean checkToken(HttpServletRequest request) {
        try {
            String jwtToken = request.getHeader("token");
            if(StringUtils.isEmpty(jwtToken)) return false;
            Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 获取标签header中的token
     */
    public static Claims getJwtToken(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwtToken)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(APP_SECRET).parseClaimsJws(jwtToken);
        return claimsJws.getBody();
    }

    /**
     * 根据token获得用户id
     * @param request 获取前端发来的请求
     * @return 返回当前发请求用户的用户id
     */
    public static String getUserId(HttpServletRequest request) {
        Claims claims = JWTUtils.getJwtToken(request);
//        以下参数名要与生成token时封装的参数名一致

        assert claims != null;
        return (String)claims.get("userId");
    }

    /**
     * 根据token获得用户角色
     * @param request 前端发来的请求
     * @return 返回当前用户的角色
     */
    public static String getRoles(HttpServletRequest request) {
        Claims claims = JWTUtils.getJwtToken(request);
//        以下参数名要与生成token时封装的参数名一致
        assert claims != null;
        return (String)claims.get("roles");
    }

    /**
     * 根据token获得用户名
     * @param request 获取前端发来的请求
     * @return 返回当前发请求用户的用户id
     */
    public static String getUserName(HttpServletRequest request) {
        Claims claims = JWTUtils.getJwtToken(request);
//        以下参数名要与生成token时封装的参数名一致
        assert claims != null;
        return (String)claims.get("username");
    }
}
