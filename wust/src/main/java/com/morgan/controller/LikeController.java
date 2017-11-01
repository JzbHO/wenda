package com.morgan.controller;

import com.morgan.model.Comment;
import com.morgan.model.EntityType;
import com.morgan.model.HostHolder;
import com.morgan.service.LikeService;
import com.morgan.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.html.parser.Entity;

/**
 * Created by Administrator on 2017/10/31 0031.
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;
    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if(hostHolder==null){
            return WendaUtil.toJsonString(999);
        }
        long likeCount=likeService.like(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION,commentId);
        return WendaUtil.toJsonString(0,String.valueOf(likeCount));
    }

    @RequestMapping(path = {"/dislike"}, method = {RequestMethod.POST})
    @ResponseBody
    public String dislike(@RequestParam("commentId") int commentId) {
        if(hostHolder==null){
            return WendaUtil.toJsonString(999);
        }
        long likeCount=likeService.dislike(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION,commentId);
        return WendaUtil.toJsonString(0,String.valueOf(likeCount));
    }




}
