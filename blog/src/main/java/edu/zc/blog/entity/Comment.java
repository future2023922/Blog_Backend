package edu.zc.blog.entity;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.baomidou.mybatisplus.annotation.*;
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
@ApiModel(value="评论对象",description="评论信息")
public class Comment implements Serializable {

    private static final long serialVersionUID=1L;


    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "评论所属的文章")
    private String blogId;

    @ApiModelProperty(value = "评论者的id")
    private String userId;

    @ApiModelProperty(value = "评论者的用户名")
    @TableField(exist = false)
    private String username;

    @ApiModelProperty(value = "评论的内容")
    private String content;

    @ApiModelProperty(value = "评论的父id")
    private String parentCommentId;

    @ApiModelProperty(value = "子评论")
    @TableField(exist = false)
    private List<Comment> children;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer isDeleted;

    @TableField(fill= FieldFill.INSERT)
    private Date createTime;


}
