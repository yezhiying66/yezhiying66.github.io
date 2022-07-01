package com.wb.controller.admin;

import com.wb.pojo.Blog;
import com.wb.pojo.BlogQuery;
import com.wb.pojo.User;
import com.wb.service.BlogService;
import com.wb.service.TagService;
import com.wb.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 动态的查询分页的博客信息
     * @param pageable
     * @param blog
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("pageInfo", blogService.listBlog(pageable, blog));
        return "admin/blogs";
    }
    @PostMapping("/blogs/search")
    public String blogSearch(@PageableDefault(size = 5, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        BlogQuery blog, Model model) {
        model.addAttribute("pageInfo", blogService.listBlog(pageable, blog));
        return "admin/blogs :: blogList"; /* 返回的是一个数据片段，返回到 blogs页面的 table中 */
    }

    private void setTypeAndTag(Model model) {
        model.addAttribute("types", typeService.listType());
        model.addAttribute("tags", tagService.listTag());
    }

    /**
     * 跳转到博客新增页面
     * @param model
     * @return
     */
    @GetMapping("/blogs/input")
    public String input(Model model) {
        setTypeAndTag(model);
        model.addAttribute("blog", new Blog());
        return "admin/blogs-input";
    }

    /**
     * 保存新增的博客信息
     * @param blog
     * @param attributes
     * @param session
     * @return
     */
    @PostMapping("/addBlogs")
    public String add_update_Blog(Blog blog, RedirectAttributes attributes, HttpSession session) {
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));

        Blog b;
        if (blog.getId() == null) {
            b = blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if (b == null) {
            attributes.addFlashAttribute("message", "操作失败！");
        } else {
            attributes.addFlashAttribute("message", "操作成功！");
        }
        return "redirect:/admin/blogs";
    }

    /**
     * 修改博客信息：与新增博客页面共用
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable("id") Integer id, Model model) {
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog", blog);
        return "admin/blogs-input";
    }

    /**
     * 删除博客信息
     * @param id
     * @param attributes
     * @return
     */
    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable("id") Integer id, RedirectAttributes attributes) {
        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message", "删除成功！");
        return "redirect:/admin/blogs";
    }
}
