package com.wb.controller.admin;

import com.wb.pojo.Type;
import com.wb.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 分页查询分类信息
     *
     * @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC, page = 0)
     * @PageableDefault(每页数据记录数, 根据什么属性进行排序, 排序规则, 页数默认为0)
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("pageInfo", typeService.listType(pageable));
        return "admin/types";
    }

    /**
     * 跳转到分类新增页面
     * @return
     */
    @GetMapping("/types/input")
    public String inputPage(Model model) {
        model.addAttribute("type", new Type());
        return "admin/types-input";
    }

    /**
     * 新增分类信息
     *
     * @Valid 表示要校验当前对象
     *
     *  Type type, BindingResult result： BindingResult必须紧跟 Type后面，否则绑定的信息无效
     *
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/addTypes")
    public String input(@Valid Type type, BindingResult result, RedirectAttributes attributes) {
        Type typeName = typeService.getTypeByName(type.getName());
        if (typeName != null) {
            result.rejectValue("name","nameError","分类名称不能重复");
            /*model.addAttribute("typeNameError", "分类名称不能重复");
            return "admin/types-input";*/
        }

        if (result.hasErrors()) {
            /* 如果有错误，则返回到新增页面 */
            return "admin/types-input";
        }

        Type t = typeService.saveType(type);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败！");
        } else {
            attributes.addFlashAttribute("message", "新增成功！");
        }
        return "redirect:/admin/types";
    }

    /**
     * 修改分类信息：与新增分类页面共用
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/types/{id}/input")
    public String editType(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 提交修改的分类信息保存到数据库
     * @param type
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/addTypes/{id}")
    public String editInput(@Valid Type type, BindingResult result, @PathVariable("id") Integer id,
                            RedirectAttributes attributes) {
        Type typeName = typeService.getTypeByName(type.getName());
        if (typeName != null) {
            result.rejectValue("name","nameError","分类名称不能重复");
            /*model.addAttribute("typeNameError", "分类名称不能重复");
            return "admin/types-input";*/
        }

        if (result.hasErrors()) {
            /* 如果有错误，则返回到新增页面 */
            return "admin/types-input";
        }

        Type t = typeService.updateType(id, type);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败！");
        } else {
            attributes.addFlashAttribute("message", "更新成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/types";
    }
}
