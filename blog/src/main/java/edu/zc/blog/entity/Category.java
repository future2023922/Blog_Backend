package edu.zc.blog.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@ApiModel(value="分类对象", description="文章分类")
public class Category implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "分类id")
    @TableId(value = "category_id", type = IdType.ID_WORKER_STR)
    private String categoryId;

    @ApiModelProperty(value = "分类名")
    private String categoryName;

    @ApiModelProperty(value = "用户id")
    private String authorId;
}
