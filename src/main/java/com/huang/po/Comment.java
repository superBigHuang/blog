package com.huang.po;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-16 13:36
 */
@Data
@Entity
@Table()
@Proxy(lazy = false)
public class Comment {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String email;
    //头像
    private String avatar;
    private String content;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    //是否为管理员的评论
    private boolean adminComment;

    @ManyToOne()
    private Blog blog;

    /**
     * comment的内联关系
     * 一个子类comment只能对应其父级的comment
     * 父类下的回复都被封装在list中
     *
     */
    @OneToMany(mappedBy = "parentComment",fetch = FetchType.EAGER)
    private List<Comment> replyComment = new ArrayList<>();
    @ManyToOne(fetch = FetchType.EAGER)
    private Comment parentComment;

    public Comment() {
    }
}
