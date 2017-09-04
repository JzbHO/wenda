package com.nowcoder.controller;

import com.nowcoder.model.Comment;
import com.nowcoder.model.EntityType;
import com.nowcoder.model.HostHolder;
import com.nowcoder.service.CommentService;
import com.nowcoder.service.QuestionService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.websocket.server.PathParam;
import java.util.Date;

/**
 * Created by Administrator on 2017/8/12 0012.
 */
@Controller
public class CommentController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;

    //对应前端 <action="/addComment">
    @RequestMapping(path={"/addComment"},method = {RequestMethod.POST})
    public String add(@RequestParam("questionId") int questionId,
                      @RequestParam("content") String content){
       try {
           Comment comment = new Comment();
           comment.setContent(content);
           comment.setEntityId(questionId);
           if (hostHolder.getUser() != null) {
               comment.setUserId(hostHolder.getUser().getId());
           } else {
               return "redirect:/reglogin";
           }
           comment.setCreatedDate(new Date());
           comment.setEntityType(EntityType.ENTITY_QUESTION);
           commentService.addComment(comment);

           int count=commentService.getCommentCount(comment.getEntityId(),comment.getEntityType());
           questionService.updateCommentCount(comment.getEntityId(),count);

       }catch (Exception e){
            logger.error("增加评论失败"+e.getMessage());
       }
       // localhost:8080/question
       return "redirect:/question/"+questionId;
    }




}
