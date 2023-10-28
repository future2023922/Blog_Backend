package edu.zc.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zc.blog.entity.Blog;
import edu.zc.blog.entity.Vo.BlogQuery;
import edu.zc.blog.service.BlogService;
import edu.zc.globalException.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
@Api(tags = "博客模块")
@RestController
@CrossOrigin
@RequestMapping("/blog")
public class BlogController {

    @Autowired
    BlogService blogService;

    @ApiOperation(value="新增一篇某个用户的博客信息")
    @PostMapping("/addBlog")
    public Results addBlog(@RequestBody BlogQuery blogQuery, HttpServletRequest request){
        boolean save = blogService.saveBlog(blogQuery,request);
        if(save)
            return Results.success();
        return Results.fail();
    }

    @ApiOperation(value="获取某篇博客信息")
    @GetMapping("/getBlog/{id}")
    public Results getBlog(@ApiParam(value = "id",required = true)@PathVariable String id, HttpServletRequest request){
        Map<String, Object> oneBlog = blogService.getOneBlog(id, request);
        return Results.success().data(oneBlog);
    }

    @ApiOperation(value="分页获取博客信息")
    @PostMapping("/getAllOfAllBlog/{current}/{limit}")
    public Results getAllOfUserBlog(@ApiParam(name="current",value="当前页",required = true)@PathVariable long current,
                                    @ApiParam(name="limit",value="每页记录数",required = true)@PathVariable long limit,
                                    @ApiParam(name="blog",value="博客信息")@RequestBody BlogQuery blogQuery,
                                    HttpServletRequest request){

        return Results.success().data(blogService.getAllOfUserBlog(current,limit,blogQuery,request));

    }

    @ApiOperation(value="修改某个用户的博客信息")
    @PostMapping("/updateBlog")
    public Results updateBlog(@ApiParam(value="blog",required = true)@RequestBody Blog blog){
        boolean update = blogService.updateById(blog);
        if(update)
            return Results.success();
        return Results.fail();
    }

    @ApiOperation(value="删除某个用户的博客信息")
    @DeleteMapping("/deleteBlog/{blogId}")
    public Results deleteUser(@ApiParam(value="blogId",required = true)@PathVariable Long blogId){
        boolean remove = blogService.removeById(blogId);
        if(remove)
            return Results.success();
        return Results.fail();
    }


}

