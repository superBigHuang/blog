package com.huang.web;

import com.huang.po.Tag;
import com.huang.service.BlogService;
import com.huang.service.TagService;
import com.huang.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author huangneng
 * @create 2020-04-30 18:08
 */
@Controller
public class TagShowController {

    @Autowired
    private TagService tagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String tags(@PageableDefault(
            size = 5,
            sort = {"updateTime"},
            direction = Sort.Direction.DESC)
                        Pageable pageable,
                        Model model,
                        @PathVariable Long id){
        //传入的10000是因为查询足够多的条数，相当于查询全部
        List<Tag> tags = tagService.listTagTop(10000);
        if (id == -1) {
            id = tags.get(0).getId();
        }
        //将原来写的凭借查询仅传入id来达到通过id查询的效果
        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(id,pageable));
        model.addAttribute("activeTagId",id);
        return "tags";
    }
}
