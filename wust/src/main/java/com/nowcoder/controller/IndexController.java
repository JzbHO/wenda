package com.nowcoder.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Created by Administrator on 2017/10/25 0025.
 */
@Controller
public class IndexController {
    @RequestMapping(path = {"/vm"}, method = {RequestMethod.GET})
    public String template(Model model) {
        return "home";
    }
}
