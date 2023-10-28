package edu.zc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zc.blog.entity.Blog;
import edu.zc.blog.entity.Category;
import edu.zc.blog.entity.Vo.CategoryQuery;
import edu.zc.blog.mapper.BlogMapper;
import edu.zc.blog.mapper.CategoryMapper;
import edu.zc.blog.service.CategoryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zc.utils.JWTUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Resource
    CategoryMapper categoryMapper;

    @Resource
    BlogMapper blogMapper;

    @Autowired
    CategoryService categoryService;

    public boolean saveCategoryQuery(CategoryQuery categoryQuery,HttpServletRequest request){
        //        保存用户id
        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("未获取到userId");
        }

//        判断该用户下的分类名是否重复
        Boolean repeat = repeat(categoryQuery,userId);
        if(!repeat){
            return false;
        }

//        保存分类名和分类id
        Category category = new Category();
        BeanUtils.copyProperties(categoryQuery,category);

        category.setAuthorId(userId);
        int insert = baseMapper.insert(category);

        return insert > 0;
    }

    private Boolean repeat(CategoryQuery categoryQuery,String userId) {
        QueryWrapper<Category> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("category_name",categoryQuery.getCategoryName());
        queryWrapper.eq("author_id",userId);
        Category category = categoryMapper.selectOne(queryWrapper);
        return category == null;
    }


    public boolean saveDefaultCategoryQuery(CategoryQuery categoryQuery,String userId){

//        保存分类名和分类id
        Category category = new Category();
        BeanUtils.copyProperties(categoryQuery,category);
//        保存用户id
        category.setAuthorId(userId);
        int insert = baseMapper.insert(category);

        return insert > 0;
    }

    public Map<String,Object> selectAllCategory(HttpServletRequest request){
//        获取token中的userId
        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }
//        确认此用户为管理员还是普通用户
        String roleName = JWTUtils.getRoles(request);
        String userName = JWTUtils.getUserName(request);
        List<CategoryQuery> allCategory = null;
        List<CategoryQuery> needFilterCategory;
        if(roleName.equals("admin")){
//            得到所有用户的所有分类
            needFilterCategory = categoryMapper.getAllCategory();
//            筛选掉所有用户的默认分类
            Iterator<CategoryQuery> it = needFilterCategory.iterator();
            allCategory = new ArrayList<>();
            while(it.hasNext()){
                CategoryQuery categoryInfo = it.next();
                if(!(categoryInfo.getCategoryName().equals("默认分类")) || categoryInfo.getUserName().equals(userName)){
                    allCategory.add(categoryInfo);
                }
            }
        }else if(roleName.equals("user")){
            allCategory = categoryMapper.getMyCategory(userId);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("categoryList",allCategory);
        return map;
    }

    @Override
    public Map<String, Object> selectCategoryAndBlogCount(HttpServletRequest request) {
        //        获取token中的userId
        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }
        //        确认此用户为管理员还是普通用户
        String roleName = JWTUtils.getRoles(request);
        List<CategoryQuery> allCategory = null;
        if(roleName.equals("admin")){
            allCategory = categoryMapper.getCategoryCount();
        }else if(roleName.equals("user")){
            allCategory = categoryMapper.getMyCategoryCount(userId);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("categoryList",allCategory);
        return map;
    }

    @Override
    public Boolean deleteByIdOfCategory(Long categoryId) {
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();
//        查看该分类下是否有博客
        queryWrapper.eq("category_id",categoryId);
//        如果有则删除
        blogMapper.delete(queryWrapper);
//        然后再删除分类
        return categoryService.removeById(categoryId);
    }

}
