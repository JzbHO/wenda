package com.morgan.controller;

import com.morgan.model.Comment;
import com.morgan.model.EntityType;
import com.morgan.model.HostHolder;
import com.morgan.model.Question;
import com.morgan.service.CommentService;
import com.morgan.service.LikeService;
import com.morgan.service.QuestionService;
import com.morgan.service.UserService;
import com.morgan.util.ViewObject;
import com.morgan.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/27 0027.
 */
@Controller
public class QuestionController {

    @Autowired
    QuestionService questionService;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    @Autowired
    LikeService likeService;



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
    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        List<Comment> list=commentService.getComment(qid,EntityType.ENTITY_QUESTION);

        List<ViewObject> vos=new ArrayList<>();
        for(Comment comment:list){
            ViewObject vo=new ViewObject();
            vo.set("comment",comment);
            if(hostHolder.getUser()==null){
                vo.set("like",0);
            }else {
                vo.set("like",likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,qid));
            }

            vo.set("likeCount",likeService.getLikeCount(EntityType.ENTITY_QUESTION,qid));
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments",vos);
        return "detail";

    }


}
