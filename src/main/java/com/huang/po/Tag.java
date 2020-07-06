package com.huang.po;

import lombok.Data;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-16 13:36
 */
@Data
@Entity
@Table()
@Proxy(lazy = false)
public class Tag {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "tags",fetch = FetchType.EAGER)
    private List<Blog> blogs = new ArrayList<Blog>();

    public Tag() {
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", blogs=" + blogs +
                '}';
    }
}
