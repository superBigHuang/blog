package com.huang.service;

import com.huang.NotFoundException;
import com.huang.dao.TagRepository;
import com.huang.po.Tag;
import com.huang.po.Type;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-17 14:28
 */
@Transactional
@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    //增删改放在事务中
    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).get();
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public List<Tag> listTagTop(Integer size) {

        Sort by = Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable = PageRequest.of(0, size, by);
        return tagRepository.findTop(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Long id, Tag tag) {

        Tag tag1 = tagRepository.getOne(id);
        if (tag1 == null) {
            throw new NotFoundException("不存在该类型");
        }
        //将tag转换成tag1
        BeanUtils.copyProperties(tag, tag1);
        return tagRepository.save(tag1);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Override
    public List<Tag> listTag(String ids) {
        //ids = 1,2,3
        //return tagRepository.findAllById(covertToList(ids));
        List<Tag> tags = new ArrayList<>();
        List<Long> longs = covertToList(ids);
        for (Long id : longs) {
            Tag tag = getTag(id);
            tags.add(tag);
        }
        return tags;
    }

    @Override
    @Transactional
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    //将1，2，3转换成[1,2,3]
    public List<Long> covertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idsArray = ids.split(",");
            for (String s : idsArray) {
                list.add(new Long(s));
            }
        }
        return list;
    }
}
