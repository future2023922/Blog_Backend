package edu.zc.blog.controller;


import edu.zc.blog.entity.Category;
import edu.zc.blog.entity.Vo.CategoryQuery;
import edu.zc.blog.service.CategoryService;
import edu.zc.globalException.Results;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
@Api(tags = "分类模块")
@RestController
@CrossOrigin
@RequestMapping("/blog/category")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

//    添加分类时，如果分类名在数据库中已存在，且authorId相同，提示已存在
    @ApiOperation(value="新增一个文章分类")
    @PostMapping("/addCategory")
    public Results addCategory(@RequestBody CategoryQuery categoryQuery,HttpServletRequest request){

        boolean save = categoryService.saveCategoryQuery(categoryQuery, request);
        if(save){
            return Results.success().msg("保存分类成功");
        }
        return Results.success().msg("已存在同名分类");
    }

    @ApiOperation(value="修改一个博客分类")
    @PostMapping("/updateCategory")
    public Results updateCategory(@RequestBody Category category){
        boolean update = categoryService.updateById(category);
        if(update){
            return Results.success();
        }
        return Results.fail();
    }

    @ApiOperation(value="查找所有博客分类")
    @GetMapping("/getAllOfAllCategory")
    public Results getUserAllCategory(HttpServletRequest request){
        return Results.success().data(categoryService.selectAllCategory(request));
    }


    @ApiOperation(value="获取对应用户的每个分类的博客数量")
    @GetMapping("/getCategoryBlogCount")
    public Results CategoryAndBlogCount(HttpServletRequest request){

        return Results.success().data(categoryService.selectCategoryAndBlogCount(request));
    }

    //删除某个用户的某个分类只需知道分类id（唯一性）就足够了
    @ApiOperation(value="删除某个用户的的某个分类信息")
    @DeleteMapping("/deleteCategory/{categoryId}")
    public Results deleteCategory(@PathVariable Long categoryId){
        Boolean remove = categoryService.deleteByIdOfCategory(categoryId);
        if(remove)
            return Results.success();
        return Results.fail();
    }
}

