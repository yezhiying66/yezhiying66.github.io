package com.wb.controller;



import com.wb.pojo.Yyds;
import com.wb.service.YidsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;


@Controller
public class yydsController {

    @Autowired
    private YidsService yidsService;

    @GetMapping(value = "/qq")
    public String listindex(){

        return "yids";
    }

    //密码
    @ResponseBody
    @PostMapping(value="/yyds/{a}/{b}")
    public Object yyds(@Valid Yyds yyds1, @PathVariable(name="a")String email, @PathVariable(name="b")String password,
                       HttpServletRequest request) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            System.out.println(email);
            System.out.println(password);
            yidsService.savers(yyds1);
            result.put("code", 200);
            result.put("message", "删除成功");
        } catch (Exception e) {

            result.put("code", 500);
            result.put("message", "删除失败");
        }
        return result;
    }
}
