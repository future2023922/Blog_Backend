package edu.zc.blog.entity.Vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author: keeplooking
 * @since: 2022/01/03 - 21:07
 */

@Data
public class CategoryQuery {

    @ApiModelProperty(value = "分类id")
    private String categoryId;

    @ApiModelProperty(value = "分类名")
    private String categoryName;

    @ApiModelProperty(value = "用户名")
    private String userName;

    @ApiModelProperty(value= "该分类下的博客数")
    private Integer blogCount;
}
