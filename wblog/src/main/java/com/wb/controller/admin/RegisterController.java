package com.wb.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Created by Banyue on 2022/3/18 0018 14:29
 */
@Controller
public class RegisterController {

    @GetMapping("/register")
    public String register() {
        return "admin/register";
    }
}
