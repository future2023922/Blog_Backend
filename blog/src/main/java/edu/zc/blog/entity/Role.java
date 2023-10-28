package edu.zc.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: keeplooking
 * @since: 2022/02/25 - 18:11
 */

@Data
public class Role {

    @TableId(value = "id",type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "角色名")
    private String role;
}
