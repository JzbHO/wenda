package com.morgan.controller;

import com.morgan.model.Comment;
import com.morgan.model.EntityType;
import com.morgan.model.HostHolder;
import com.morgan.model.Question;
import com.morgan.service.*;
import com.morgan.util.ViewObject;
import com.morgan.util.WendaUtil;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    FollowService followService;

    @Autowired
    SearchService searchService;

    @Autowired
    HostHolder hostHolder;
    private static final Logger logger=LoggerFactory.getLogger(QuestionController.class);

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
            return WendaUtil.toJsonString(0,"");
        }else {
            return WendaUtil.toJsonString(1,"失败");
        }
    }
    @RequestMapping(value = "/question/{qid}", method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid) {
        Question question = questionService.getById(qid);
        model.addAttribute("question", question);
        int followSize=followService.getFolloweesList(EntityType.ENTITY_QUESTION,qid).size();
        model.addAttribute("followSize",followSize);

        List<Comment> list=commentService.getComment(qid,EntityType.ENTITY_QUESTION);

        List<ViewObject> vos=new ArrayList<>();
        for(Comment comment:list){
            ViewObject vo=new ViewObject();
            vo.set("comment",comment);
            if(hostHolder.getUser()==null){
                vo.set("liked",0);
            }else {
                vo.set("liked",likeService.getLikeStatus(hostHolder.getUser().getId(),EntityType.ENTITY_QUESTION,qid));
            }

            vo.set("likeCount",likeService.getLikeCount(EntityType.ENTITY_COMMENT,comment.getId()));
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        model.addAttribute("comments",vos);
        return "detail";

    }

    @RequestMapping(value = "/search", method = {RequestMethod.GET})
    public String search(Model model,@RequestParam("q") String keyword )throws Exception {
      List<Question> list=searchService.searchQuestion(keyword,0,10,"<font color=\"red\">","</font>");
      List<ViewObject> vos=new ArrayList<ViewObject>();
      for(Question question:list){
          ViewObject vo=new ViewObject();
          vo.set("question",question);
          vo.set("user",userService.getUser(question.getUserId()));
          vo.set("followCount",followService.getFolloweeCount(EntityType.ENTITY_QUESTION,question.getId()));
          vos.add(vo);
      }
      model.addAttribute("vos",vos);
      return   "result";

    }



}
