package edu.zc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import edu.zc.blog.entity.Blog;
import edu.zc.blog.mapper.BlogMapper;
import edu.zc.blog.service.ArchivesService;
import edu.zc.utils.JWTUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author: keeplooking
 * @since: 2022/02/28 - 3:05
 */

@Service
public class ArchivesServiceImpl implements ArchivesService {
    @Resource
    private BlogMapper blogMapper;

    public Map<String,Object> getArchivesList(HttpServletRequest request) {
        Page<Blog> page = new Page<>(1, 100);
        QueryWrapper<Blog> wrapper = new QueryWrapper<>();

        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }

        String roleName = JWTUtils.getRoles(request);

        if(roleName.equals("user")){
            wrapper.eq("author_id",userId);
        }

        wrapper.select("id", "title", "create_time")
                .orderByDesc("create_time");

        IPage<Blog> ipage = blogMapper.selectPage(page, wrapper);

        Map<String,Object> map = new HashMap<>();
        map.put("list",ipage);
        return map;
    }
}
