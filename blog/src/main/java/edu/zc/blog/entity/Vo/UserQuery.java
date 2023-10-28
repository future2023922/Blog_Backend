package edu.zc.blog.entity.Vo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * @author: keeplooking
 * @since: 2022/01/01 - 16:41
 */

@Data
@ToString
public class UserQuery {

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "角色名")
    private String role;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "用户注册时间")
    private LocalDateTime createTime;
}
