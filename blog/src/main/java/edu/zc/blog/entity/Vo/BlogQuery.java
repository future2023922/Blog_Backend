package edu.zc.blog.entity.Vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.Version;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author: keeplooking
 * @since: 2021/12/31 - 12:39
 */

@Data
public class BlogQuery {
    @ApiModelProperty(value = "文章Id")
    private String id;

    @ApiModelProperty(value = "文章标题")
    private String title;

    @ApiModelProperty(value = "文章内容")
    private String content;

    @ApiModelProperty(value = "文章作者名")
    private String userName;

    @ApiModelProperty(value = "文章分类名")
    private String categoryName;

    @ApiModelProperty(value = "文章版权")
    private String copyright;

    @TableField(fill= FieldFill.INSERT)
    private Date createTime;

    @TableField(fill= FieldFill.INSERT_UPDATE)
    private Date updateTimes;

    @ApiModelProperty(value = "乐观锁")
    @Version
    private Long version;
}
