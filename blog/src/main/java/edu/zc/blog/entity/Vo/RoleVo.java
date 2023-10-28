package edu.zc.blog.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: keeplooking
 * @since: 2022/02/25 - 20:24
 */

@Data
public class RoleVo {

    @ApiModelProperty(value = "角色名")
    private String role;
}
