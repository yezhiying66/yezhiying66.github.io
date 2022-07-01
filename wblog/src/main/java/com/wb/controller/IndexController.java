package com.wb.controller;

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
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private TagService tagService;

    /**
     * 首页数据信息
     * @param pageable
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(@PageableDefault(size = 6, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        model.addAttribute("pageInfo", blogService.listBlog(pageable));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTagTop(8));
        model.addAttribute("recommentBlogs", blogService.listRecommentBlogTop(5));
        return "index";
    }

    /**
     * 搜索分页查询
     * @param pageable
     * @param query
     * @param model
     * @return
     */
    @PostMapping("/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable,
                         @RequestParam("query") String query, Model model) {
        model.addAttribute("searchPage", blogService.listSearchBlog("%"+query+"%", pageable));
        model.addAttribute("query", query);
        return "search";
    }

    /**
     * 根据博客 id值进行详情页面进行数据展示
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable("id") Integer id, Model model){
        model.addAttribute("blog", blogService.getAndConvert(id));
        return "blog";
    }

    /**
     * 取 3条最新的博客信息
     * @param model
     * @return
     */
    @GetMapping("/footer/newblog")
    public String newblogs(Model model) {
        model.addAttribute("newblogs", blogService.listRecommentBlogTop(3));
        return "commons/fragment :: newblogList";
    }
}
