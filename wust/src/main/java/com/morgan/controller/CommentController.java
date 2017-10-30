package com.morgan.controller;

import com.morgan.model.Comment;
import com.morgan.model.EntityType;
import com.morgan.model.ViewObject;
import com.morgan.service.CommentService;
import com.morgan.service.MySensitiveService;
import com.morgan.service.QuestionService;
import com.morgan.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.HtmlUtils;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30 0030.
 */
@Controller
public class CommentController {
    @Autowired
    MySensitiveService mySensitiveService;

    @Autowired
    CommentService commentService;

    @Autowired
    QuestionService questionService;
    @RequestMapping(path={"/addComment"},method = {RequestMethod.POST})
    public String addComment(@RequestParam("content") String content,
                             @RequestParam("questionId") int questionId){
        Comment comment=new Comment();
        content=HtmlUtils.htmlEscape(content);
        content=mySensitiveService.filter(content);

        comment.setContent(content);
        comment.setCreatedDate(new Date());
        comment.setEntityId(questionId);
        comment.setEntityType(EntityType.ENTITY_QUESTION);
        comment.setStatus(WendaUtil.COMMENT_EFFICIENT);

        commentService.addComment(comment);
        int count=commentService.getCommentCount(comment.getEntityType(),comment.getEntityId());
        questionService.updateQuestionComment(questionId,count);

        return "redirect:/question/"+String.valueOf(comment.getEntityId());




    }

}
