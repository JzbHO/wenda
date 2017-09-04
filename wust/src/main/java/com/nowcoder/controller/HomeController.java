package com.nowcoder.controller;

import com.nowcoder.model.Question;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.UserService;
import org.omg.CORBA.Request;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;


    @RequestMapping(path = {"/","/index"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String index(Model model) throws IOException {
       // List<Question> qustionlist=questionService.getLatestQuestions(0,0,1) ;
        //model.addAttribute("questions",qustionlist);
        return "header";
    }
//    @RequestMapping(path={"/","/index"},method = {RequestMethod.GET})
//    public String index(HttpSession httpSession){
//        return "index";
//    }


}
