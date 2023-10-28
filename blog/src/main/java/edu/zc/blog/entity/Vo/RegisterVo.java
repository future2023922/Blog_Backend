package edu.zc.blog.entity.Vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: keeplooking
 * @since: 2022/02/24 - 16:05
 */

@Data
@ApiModel(value="注册用户", description="注册用户")
public class RegisterVo {

    @ApiModelProperty(value = "昵称")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "邮箱")
    private String email;
}
