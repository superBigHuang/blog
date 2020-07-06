package com.huang.service;

import com.huang.po.Tag;
import com.huang.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-17 14:25
 */
public interface TagService {

    Tag getTagByName(String name);

    //保存分类
    Tag saveTag(Tag tag);

    //根据id获得分页
    Tag getTag(Long id);

    //分页查询
    Page<Tag> listTag(Pageable pageable);



    //先获取id在修改
    Tag updateTag(Long id, Tag tag);

    void deleteTag(Long id);

    //查出所有标签
    List<Tag> listTag();

    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);





}
