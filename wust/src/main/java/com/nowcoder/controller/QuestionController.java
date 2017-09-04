package com.nowcoder.controller;

import com.nowcoder.model.*;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.QuestionService;
import com.nowcoder.service.UserService;
import com.nowcoder.util.WendaUtil;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2017/7/20 0020.
 */
@Controller
public class QuestionController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    QuestionService questionService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    UserService userService;

    //{ }定义路径里的参数
    @RequestMapping(value="/question/{qid}",method = {RequestMethod.GET})
    public String questionDetail(Model model, @PathVariable("qid") int qid){
        Question question=questionService.getById(qid);
        model.addAttribute("question",question);
        List<Comment> commentList=commentService.getComment(qid, EntityType.ENTITY_QUESTION);
        List<ViewObject> comments=new ArrayList<ViewObject>();

        //封装
        for(Comment comment:commentList){
            ViewObject vo=new ViewObject();
            vo.set("comment",comment);
            vo.set("user",userService.getUser(comment.getUserId()));
            comments.add(vo);
        }

        model.addAttribute("comments",comments);
        //datil.html页面如何取值?  comments.user.headUrl
        /**
         *   #foreach ($comment in $comments)
         *      ${comment.user.headUrl}
         *   #end
         *   函数
         *   $date.format('yyyy-MM-dd HH:mm:ss',$conversation.message.createdDate)
         *
         */
        return "datil";
    }


















    @RequestMapping(value ="/question/add",method = {RequestMethod.POST})
    @ResponseBody
    public String addquestion(@RequestParam("title") String title,@RequestParam("content") String content){
       try {
           Question question = new Question();
           question.setContent(content);
           question.setTitle(title);
           question.setCreatedDate(new Date());
           question.setCommentCount(0);
           if (hostHolder.getUser() == null)
               question.setUserId(WendaUtil.ANONY_USER);
           else
               question.setUserId(hostHolder.getUser().getId());
            if(questionService.addQuestion(question)>0)
                return WendaUtil.getJSONString(0);

       }catch (Exception e){
           logger.error("增加题目失败"+e.getMessage());
       }
       return WendaUtil.getJSONString(1,"失败");
    }



}