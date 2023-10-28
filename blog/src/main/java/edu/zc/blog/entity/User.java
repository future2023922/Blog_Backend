package edu.zc.blog.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="用户对象", description="用户信息")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "用户的唯一id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "用户密码")
    private String password;

    @ApiModelProperty(value = "用户角色名")
    private String roleId;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "性别")
    private String sex;

    @ApiModelProperty(value = "用户的个性签名")
    private String sign;

    @ApiModelProperty(value = "用户的头像地址")
    private String avatar;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value="乐观锁")
    @Version
    private Long version;

    @TableField(fill= FieldFill.INSERT)
    private Date createTime;


}
