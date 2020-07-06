package com.huang.service;

import com.huang.dao.CommentRepository;
import com.huang.po.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-30 10:37
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort = Sort.by(Sort.Direction.ASC, "createTime");
        //拿到父节点为空的数据
        List<Comment> comments = commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long commentParentId = comment.getParentComment().getId();
        if (commentParentId != -1) {
            comment.setParentComment(commentRepository.getOne(commentParentId));
        } else {
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }


    //循环每个顶级的评论节点
    private List<Comment> eachComment(List<Comment> comments){
        //新建一个comment集合并且拷贝进去数据，避免数据库数据改变
        List<Comment> commentView = new ArrayList<>();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentView.add(c);
        }
        //合并评论的各层子代评论到第一级自带评论集合中
        combineChildren(commentView);
        return commentView;
    }

    private void combineChildren(List<Comment> comments) {
        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComment();
            for (Comment reply1 : replys1) {
                //循环迭代，找出子代，存在在tempReply中
                recursicely(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComment(tempReplys);
            //清空临时存放区
            tempReplys = new ArrayList<>();
        }
    }

    //存放迭代找出的所有子代的集合
    private List<Comment> tempReplys = new ArrayList<>();

    //递归遍历是否有子代
    private void recursicely(Comment comment) {
        //顶节点添加到临时存放结合
        tempReplys.add(comment);
        if (comment.getReplyComment().size()>0){
            List<Comment> replys = comment.getReplyComment();
            for (Comment reply : replys) {
                tempReplys.add(reply);
                if (reply.getReplyComment().size()>0){
                    recursicely(reply);
                }
            }
        }
    }
}
