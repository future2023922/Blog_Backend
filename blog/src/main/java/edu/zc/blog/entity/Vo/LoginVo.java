package edu.zc.blog.entity.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: keeplooking
 * @since: 2022/02/23 - 21:07
 */

@Data
@ApiModel(value="登录对象", description="登录对象")
public class LoginVo {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

}
