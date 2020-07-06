package com.huang.service;

import com.huang.po.Comment;

import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-30 10:36
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);
}
