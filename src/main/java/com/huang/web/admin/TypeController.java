package com.huang.web.admin;

import com.huang.po.Type;
import com.huang.service.TypeService;
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
public class TypeController {

    @Autowired
    private TypeService typeService;

    //springboot自动将查询到的数据封装到Pageable中
    //并且按照id倒序方式排序
    @GetMapping("/types")
    public String types(@PageableDefault(size = 8,
            sort = {"id"},
            direction = Sort.Direction.DESC)
                                Pageable pageable,
                        Model model) {

        model.addAttribute("page", typeService.listType(pageable));
        return "admin/types";

    }

    @GetMapping("/types/input")
    public String input(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";

    }

    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable Long id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    //BindingResult 必须在 Type 后面 不然校验失败
    @PostMapping("/types/{id}")
    public String editPost(@Valid Type type, BindingResult result, RedirectAttributes attributes, @PathVariable Long id) {

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }

        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败");
        } else {
            attributes.addFlashAttribute("message", "更新成功");
        }
        return "redirect:/admin/types";

    }

    @PostMapping("/types")
    public String editPost(@Valid Type type, BindingResult result, RedirectAttributes attributes) {

        Type type1 = typeService.getTypeByName(type.getName());
        if (type1 != null) {
            result.rejectValue("name", "nameError", "不能重复添加分类");
        }

        if (result.hasErrors()) {
            return "admin/types-input";
        }
        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "添加失败");
        } else {
            attributes.addFlashAttribute("message", "添加成功");
        }
        return "redirect:/admin/types";

    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";

    }
}
