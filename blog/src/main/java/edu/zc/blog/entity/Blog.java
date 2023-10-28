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
@ApiModel(value="博客对象", description="博客信息")
public class Blog implements Serializable {

    private static final long serialVersionUID=1L;


    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    private String title;

    @ApiModelProperty(value = "文章作者id")
    private String authorId;

    @ApiModelProperty(value = "文章分类id")
    private String categoryId;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章版权")
    private String copyright;

    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTimes;

    @ApiModelProperty(value = "逻辑删除")
    @TableLogic
    private Integer isDeleted;

    @ApiModelProperty(value="乐观锁")
    @Version
    private Long version;

}
