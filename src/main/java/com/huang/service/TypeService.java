package com.huang.service;

import com.huang.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-17 14:25
 */
public interface TypeService {

    Type getTypeByName(String name);

    //保存分类
    Type saveType(Type type);

    //根据id获得分页
    Type getType(Long id);

    //分页查询
    Page<Type> listType(Pageable pageable);

    //先获取id在修改
    Type updateType(Long id,Type type);

    void deleteType(Long id);

    List<Type> listType();

    //取出size个元素的列表
    List<Type> listTypeTop(Integer size);

}
