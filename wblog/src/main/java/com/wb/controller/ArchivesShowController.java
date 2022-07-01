package com.wb.controller;

import com.wb.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
@Controller
public class ArchivesShowController {

    @Autowired
    private BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model) {
        model.addAttribute("archiveMap", blogService.archivesBlog());
        model.addAttribute("blogCount", blogService.countBlog());
        return "archives";
    }
}
