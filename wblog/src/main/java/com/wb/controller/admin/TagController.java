package com.wb.controller.admin;

import com.wb.pojo.Tag;
import com.wb.service.TagService;
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
 * Created by Banyue on 2022/3/18 0018 14:29
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    private TagService tagService;

    /**
     * 分页查询标签信息
     *
     * @PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC, page = 0)
     * @PageableDefault(每页数据记录数, 根据什么属性进行排序, 排序规则, 页数默认为0)
     *
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/tags")
    public String tags(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("pageInfo", tagService.listTag(pageable));
        return "admin/tags";
    }

    /**
     * 跳转到标签新增页面
     * @return
     */
    @GetMapping("/tags/input")
    public String inputPage(Model model) {
        model.addAttribute("tag", new Tag());
        return "admin/tags-input";
    }

    /**
     * 新增标签信息
     *
     * @Valid 表示要校验当前对象
     *
     *  Tag tag, BindingResult result： BindingResult必须紧跟 Tag后面，否则绑定的信息无效
     *
     * @param tag
     * @param attributes
     * @return
     */
    @PostMapping("/addTags")
    public String input(@Valid Tag tag, BindingResult result, RedirectAttributes attributes) {
        Tag tagName = tagService.getTagByName(tag.getName());
        if (tagName != null) {
            result.rejectValue("name","nameError","标签名称不能重复");
            /*model.addAttribute("tagNameError", "标签名称不能重复");
            return "admin/tags-input";*/
        }

        if (result.hasErrors()) {
            /* 如果有错误，则返回到新增页面 */
            return "admin/tags-input";
        }

        Tag t = tagService.saveTag(tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "新增失败！");
        } else {
            attributes.addFlashAttribute("message", "新增成功！");
        }
        return "redirect:/admin/tags";
    }

    /**
     * 修改标签信息：与新增标签页面共用
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/tags/{id}/input")
    public String editTag(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("tag", tagService.getTag(id));
        return "admin/tags-input";
    }

    /**
     * 提交修改的标签信息保存到数据库
     * @param tag
     * @param result
     * @param id
     * @param attributes
     * @return
     */
    @PostMapping("/addTags/{id}")
    public String editInput(@Valid Tag tag, BindingResult result, @PathVariable("id") Integer id,
                            RedirectAttributes attributes) {
        Tag tagName = tagService.getTagByName(tag.getName());
        if (tagName != null) {
            result.rejectValue("name","nameError","标签名称不能重复");
            /*model.addAttribute("tagNameError", "标签名称不能重复");
            return "admin/tags-input";*/
        }

        if (result.hasErrors()) {
            /* 如果有错误，则返回到新增页面 */
            return "admin/tags-input";
        }

        Tag t = tagService.updateTag(id, tag);
        if (t == null) {
            attributes.addFlashAttribute("message", "更新失败！");
        } else {
            attributes.addFlashAttribute("message", "更新成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        tagService.deleteTag(id);
        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/tags";
    }
}
