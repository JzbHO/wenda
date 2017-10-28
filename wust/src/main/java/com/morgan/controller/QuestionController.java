package com.morgan.controller;

import com.morgan.model.HostHolder;
import com.morgan.model.Question;
import com.morgan.service.QuestionService;
import com.morgan.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created by Administrator on 2017/10/27 0027.
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;
    @RequestMapping(path={"/question/add"},method = {RequestMethod.POST})
    @ResponseBody
    public String addQuestion(@RequestParam("title") String title,@RequestParam("content") String content){
        Question question=new Question();
        question.setCommentCount(0);
        question.setContent(content);
        question.setCreatedDate(new Date());
        question.setTitle(title);

        if(hostHolder.getUser()!=null) {
            question.setUserId(hostHolder.getUser().getId());
        }else {
            return WendaUtil.toJsonString(999,"");
        }

        if(questionService.addQuestion(question)>0){
            return WendaUtil.toJsonString(0);
        }else {
            return WendaUtil.toJsonString(1,"失败");
        }



    }

}
