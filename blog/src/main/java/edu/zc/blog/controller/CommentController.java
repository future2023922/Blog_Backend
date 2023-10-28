package edu.zc.blog.controller;


import edu.zc.blog.entity.Comment;
import edu.zc.blog.service.CommentService;
import edu.zc.globalException.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
@Api(tags = "评论模块")
@RestController
@CrossOrigin
@RequestMapping("/blog/comment")
public class CommentController {
    @Autowired
    CommentService commentService;

    @ApiOperation(value="新增一个文章评论")
    @PostMapping("/addComment")
    public Results addComment(@RequestBody Comment comment, HttpServletRequest request){
        boolean save = commentService.saveComment(comment,request);
        if(save)
            return Results.success();
        return Results.fail().msg("发布评论失败");
    }

    @ApiOperation(value = "获取文章评论")
    @GetMapping("/getCommentList/{blogId}")
    public Results getCommentList(@PathVariable String blogId,HttpServletRequest request){
        List<Comment> comments = commentService.commentList(blogId, request);
        return Results.success().data("commentList",comments);
    }

    @ApiOperation(value="删除文章评论")
    @DeleteMapping("/deleteComment/{commentId}")
    public Results deleteComment(@ApiParam(value="评论id",required = true)@PathVariable String commentId,HttpServletRequest request){
        boolean delete = commentService.deleteComment(commentId,request);
        if(delete)
            return Results.success();
        return Results.fail();
    }

}

