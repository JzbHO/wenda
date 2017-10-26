package com.nowcoder.controller;

import com.nowcoder.model.Question;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.QuestionService;

import com.nowcoder.service.UserService;
import org.omg.CORBA.portable.ValueInputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;


    @RequestMapping(path={"/index1/{userId}"},method = {RequestMethod.GET})
    public String index1(Model model, @PathVariable("userId") int userId){
        List<ViewObject> vos=getLatestQuestions(userId,0,5);
        model.addAttribute("vos",vos);
        return "index";
    }

    @RequestMapping(path={"/index"},method = {RequestMethod.GET})
    public String index(Model model){
        List<ViewObject> vos=getLatestQuestions(0,0,5);
        model.addAttribute("vos",vos);
        return "index";
    }
    private List<ViewObject> getLatestQuestions(int id,int offset,int num){
        List<Question> list=questionService.getLatestQuestions(id,0,10);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        for(Question question:list){
            ViewObject vo=new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }




}
