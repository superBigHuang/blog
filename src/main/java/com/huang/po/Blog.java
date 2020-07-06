package com.huang.po;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-16 13:03
 */
@Data
@Entity
@Table()
@Proxy(lazy = false)
public class Blog {

    @Id
    @GeneratedValue
    private Long id;
    private String title;
    @Basic(fetch = FetchType.LAZY)
    @Lob
    private String content;
    private String firstPicture;
    /**
     * 是否原创标签
     */
    private String flag;
    /**
     * 浏览次数
     */
    private Integer views;
    /**
     * 赞赏是否开启
     */
    private boolean appreciation;
    /**
     * 转载声明是否开启
     */
    private boolean shareStatement;
    /**
     * 评论是否开启
     */
    private boolean commentabled;
    /**
     * 是否发布
     */
    private boolean pushilded;
    /**
     * 是否推荐
     */
    private boolean recommend;
    /**
     * 创作时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    /**
     * 博客描述
     */
    private String description;

    //从网页获取的标签id 1,2,3 并且不需要保存到数据库
    @Transient
    private String tagIds;

    /**
     * 实体类关系
     */
    @ManyToOne(fetch = FetchType.EAGER)
    private Type type;

    //级联新增 如果在blog中新增了一个tag。那么该tag也会被保存
    @OrderColumn
    @ManyToMany(cascade = {CascadeType.PERSIST},fetch = FetchType.EAGER)
    private List<Tag> tags = new ArrayList<Tag>();

    @ManyToOne()
    private User user;

    @OneToMany(mappedBy = "blog",fetch = FetchType.EAGER)
    private List<Comment> comments = new ArrayList<>();


    public Blog() {
    }

    public void init() {
        this.tagIds = tagsToIds(this.getTags());
    }

    private String tagsToIds(List<Tag> tags) {
        if (!tags.isEmpty()) {
            StringBuffer ids = new StringBuffer();
            boolean flag = false;
            for (Tag tag : tags) {
                if (flag) {
                    ids.append(",");
                } else {
                    flag = true;
                }
                ids.append(tag.getId());
            }
            return ids.toString();
        } else {
            return tagIds;
        }
    }

    @Override
    public String toString() {
        return "Blog{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", firstPicture='" + firstPicture + '\'' +
                ", flag='" + flag + '\'' +
                ", views=" + views +
                ", appreciation=" + appreciation +
                ", shareStatement=" + shareStatement +
                ", commentabled=" + commentabled +
                ", pushilded=" + pushilded +
                ", recommend=" + recommend +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", tagIds='" + tagIds + '\'' +
                ", type=" + type +
                ", user=" + user +
                '}';
    }
}
