package com.huang.po;

import lombok.Data;
import org.hibernate.annotations.Proxy;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-16 13:36
 */
@Data
@Entity
@Table
@Proxy(lazy = false)
public class Type {

    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空")
    private String name;

    /**
     *     @OneToMany(mappedBy = "type") Type被Blog维护
     */
    @OneToMany(mappedBy = "type",fetch = FetchType.EAGER)
    private List<Blog> blogs = new ArrayList<Blog>();

    public Type() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
