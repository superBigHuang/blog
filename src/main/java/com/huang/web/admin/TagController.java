package com.huang.web.admin;
import com.huang.po.Tag;
import com.huang.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author huangneng
 * @create 2020-04-17 14:41
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    //springboot自动将查询到的数据封装到Pageable中
    //并且按照id倒序方式排序
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 8,
            sort = {"id"},
            direction = Sort.Direction.DESC)
                                Pageable pageable,
                        Model model) {

        model.addAttribute("page", tagService.listTag(pageable));
        return "admin/tags";

    }

    @GetMapping("/tags/input")
    public String input(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";

    }

    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable Long id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    //BindingResult 必须在 Tag 后面 不然校验失败
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes, @PathVariable Long id) {

        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }

        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/tags";

    }

    @PostMapping("/tags")
    public String editPost(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {

        Tag tag1 = tagService.getTagByName(tag.getName());
        if (tag1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }

        if (result.hasErrors()) {
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "添加失败");
        } else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/tags";

    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/tags";

    }
}
