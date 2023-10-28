package edu.zc.blog.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import edu.zc.blog.entity.Comment;
import edu.zc.blog.mapper.CommentMapper;
import edu.zc.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import edu.zc.globalException.exception.DiyException;
import edu.zc.utils.JWTUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author keeplooking
 * @since 2021-12-30
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    CommentService commentService;

    @Resource
    CommentMapper commentMapper;

    @Override
    public boolean saveComment(Comment comment, HttpServletRequest request) {
        String userId = "";
        try {
            userId = JWTUtils.getUserId(request);
        }catch (Exception e) {
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }
        comment.setUserId(userId);
//        如果是第一次在博客处发表评论，则设置父id为-1
        if(comment.getParentCommentId() == null){
            comment.setParentCommentId("-1");
        }
        return commentService.save(comment);
    }

    @Override
    public List<Comment> commentList(String blogId,HttpServletRequest request) {
        List<Comment> commentRoot = commentMapper.selectRootList(blogId);
        List<Comment> commentChild = commentMapper.selectChildList(blogId);
        return combineChildren(commentRoot,commentChild);
    }

    @Override
    public boolean deleteComment(String commentId, HttpServletRequest request) {
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        String userId = "";
        try{
            userId = JWTUtils.getUserId(request);
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("未获取到用户id");
        }
        queryWrapper.eq("user_id",userId)
                    .eq("id",commentId);
        if(commentMapper.selectOne(queryWrapper) == null){
            throw new DiyException(-1,"该评论不是您发布的，您无权删除");
        }
        /*
          删除评论及其子评论
         */
        ArrayList<String> commentList = new ArrayList<>();
//        找到并存储要删除的评论下的子评论id
        getDeletedChildCommentList(commentId,commentList);
//        存储最外层的评论id
        commentList.add(commentId);
        int i = commentMapper.deleteBatchIds(commentList);
        return i > 0;
    }

    /**
     * 找到并存储要删除的评论下的子评论id
     * @param commentId 父评论id
     * @param commentList 存放子评论id的list
     */
    private void getDeletedChildCommentList(String commentId, ArrayList<String> commentList) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
//        找到以用户删除的评论作为父评论的子评论id
        wrapper.eq("parent_comment_id",commentId)
                .select("id");
        List<Comment> childList = commentMapper.selectList(wrapper);
//        遍历存储子评论id
        childList.forEach(child ->{
            commentList.add(child.getId());
//        递归查询
            getDeletedChildCommentList(child.getId(),commentList);
        });
    }

    /**
     * 区分子评论、父评论，并将子评论加入父评论
     * @param commentRoot 父评论
     * @param commentChild 子评论
     * @return 返回整理好的评论列表
     */
    private List<Comment> combineChildren(List<Comment> commentRoot, List<Comment> commentChild) {
        for(Comment root: commentRoot){
            List<Comment> comments = new ArrayList<>();
            for(Comment child: commentChild){
                if(child.getParentCommentId().equals(root.getId())){
                    comments.add(child);
                }
            }
            List<Comment> comments1 = combineChildren(comments, commentChild);
            root.setChildren(comments1);
        }
        return commentRoot;
    }



}
