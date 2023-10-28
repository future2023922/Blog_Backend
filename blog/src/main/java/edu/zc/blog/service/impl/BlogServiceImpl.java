package edu.zc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zc.blog.entity.Blog;
import edu.zc.blog.entity.Category;
import edu.zc.blog.entity.User;
import edu.zc.blog.entity.Vo.BlogQuery;
import edu.zc.blog.mapper.BlogMapper;
import edu.zc.blog.mapper.CategoryMapper;
import edu.zc.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zc.blog.service.CategoryService;
import edu.zc.blog.service.UserService;
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
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Resource
    private BlogMapper blogMapper;

    @Resource
    private CategoryMapper categoryMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    BlogService blogService;

    public Map<String, Object> getAllOfUserBlog(long current,
                                                long limit,
                                                BlogQuery blogQuery,
                                                HttpServletRequest request) {
//        设定分页条件与对象
        Page<Blog> ipage = new Page<>(current, limit);
        QueryWrapper<Blog> queryWrapper = new QueryWrapper<>();

        //            获取token中的userid和role
        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }
        String roleName = JWTUtils.getRoles(request);

        if(blogQuery != null){
            String id = blogQuery.getId();
            String title = blogQuery.getTitle();
            String content = blogQuery.getContent();
            String categoryName = blogQuery.getCategoryName();
            if(id != null){
                queryWrapper.like("id",id);
            }
            if(title != null){
                queryWrapper.like("title",title);
            }
            if(content != null){
                queryWrapper.like("content",content);
            }
            if(categoryName != null){
                QueryWrapper<Category> queryWrapperOfCategory = new QueryWrapper<>();
                queryWrapperOfCategory.eq("category_name",categoryName);
                queryWrapperOfCategory.eq("author_id",userId);
//                这得保证分类名的唯一性，不然匹配的结果将不止一个分类id
                Category categorys = categoryMapper.selectOne(queryWrapperOfCategory);
                String categoryId = categorys.getCategoryId();
                queryWrapper.like("category_id",categoryId);

            }
        }

//        判断用户为管理员还是普通用户
        Integer total = 0;
//            如果角色为管理员
        if(roleName.equals("admin")){
            ipage.setRecords(blogMapper.getAllBlogs());
            total = blogMapper.selectCount(queryWrapper);
        }
//            如果角色为普通用户
        else if(roleName.equals("user")){
            ipage.setRecords(blogMapper.getMyAllBlogs(userId));
            queryWrapper.eq("author_id",userId);
            total = blogMapper.selectCount(queryWrapper);
        }
//          进行分页
        IPage<Blog> page = blogService.page(ipage, queryWrapper);

//          拿出指定页数的数据（blog对象） =》 blogQuery对象返回给前端
        List<Blog> blogs = page.getRecords();
        ArrayList<BlogQuery> list = new ArrayList<>();

        Iterator<Blog> iterator = blogs.iterator();
        while(iterator.hasNext()){
            Blog next = iterator.next();
            String authorId = next.getAuthorId();
            String categoryId = next.getCategoryId();
            User user = userService.getById(authorId);
            Category category = categoryService.getById(categoryId);
//            分类总是出问题。原因：admin在内的其他用户很早就分配了，当时还没有为博客加入默认分类，所以在查询时会出错
//            前后端一直请求的是BlogQuery对象。但在进行分页时是是根据userId查的blog表，所以只能这样遍历添加blogQuery的值,而有的用户与对应分类又不在
//            有没有更好的解决方案？
//            String categoryName;
//            if(category.getCategoryName() == null){
//                categoryName = "默认分类";
//            }else{
//                categoryName = category.getCategoryName();
//            }
//            报错原因：有该博客，但查询该博客的分类不存在
//            可能是删除分类时，并没有关联的删除分类下的博客表。
            String categoryName = category.getCategoryName();
            BlogQuery blogQuery1 = new BlogQuery();
            BeanUtils.copyProperties(next,blogQuery1);
            blogQuery1.setUserName(user.getUsername());
            blogQuery1.setCategoryName(categoryName);
            list.add(blogQuery1);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("list",list);
        map.put("total",total);

        return map;
    }

    @Override
    public boolean saveBlog(BlogQuery blogQuery, HttpServletRequest request) {

        Blog blog = new Blog();
        BeanUtils.copyProperties(blogQuery,blog);

//        获取用户id
        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }
        blog.setAuthorId(userId);

        String categoryName = blogQuery.getCategoryName();
//        获取分类id
        String categoryId = categoryMapper.getMyCategoryId(userId,categoryName);
        blog.setCategoryId(categoryId);
        return blogService.save(blog);
    }

    @Override
    public Map<String,Object> getOneBlog(String blogId, HttpServletRequest request) {

//            获取token中的userid和role
        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }

        BlogQuery blogInfo = blogMapper.getBlogInfo(userId,blogId);
        Map<String,Object> map = new HashMap<>();
        map.put("blogInfo",blogInfo);
//        删除评论时，用户只能删除自己的评论，userId用来确认是否要显示某评论的删除按钮
        map.put("userId",userId);

        return map;
    }

}
