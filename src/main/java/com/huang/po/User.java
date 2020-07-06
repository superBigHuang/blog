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
public class User {

    @Id
    @GeneratedValue
    private Long id;
    private String nickname;
    private String username;
    private String password;
    private String email;
    private Integer type;
    private String avatar;
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @OneToMany(mappedBy = "user",fetch = FetchType.EAGER)
    private List<Blog> blogs = new ArrayList<>();

    public User() {
    }
}
