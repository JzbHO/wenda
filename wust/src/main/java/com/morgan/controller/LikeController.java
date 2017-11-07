package com.morgan.controller;

import com.morgan.async.EventModel;
import com.morgan.async.EventProducer;
import com.morgan.async.EventType;
import com.morgan.model.Comment;
import com.morgan.model.EntityType;
import com.morgan.model.Feed;
import com.morgan.model.HostHolder;
import com.morgan.service.LikeService;
import com.morgan.util.TimeLineType;
import com.morgan.util.WendaUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.swing.text.html.parser.Entity;
import java.util.Date;

/**
 * Created by Administrator on 2017/10/31 0031.
 */
@Controller
public class LikeController {
    @Autowired
    LikeService likeService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;
    @RequestMapping(path = {"/like"}, method = {RequestMethod.POST})
    @ResponseBody
    public String like(@RequestParam("commentId") int commentId) {
        if(hostHolder==null){
            return WendaUtil.toJsonString(999);
        }
        EventModel eventModel=new EventModel();
        Feed feed=new Feed();
        feed.setUserId(hostHolder.getUser().getId());
        feed.setType(TimeLineType.LIKE);
        feed.setCreateDate(new Date());
        feed.setData("123");
        eventModel.setActorId(hostHolder.getUser().getId()).setEntityId(commentId).setEntityType(EntityType.ENTITY_COMMENT)
        .setType(EventType.LIKE).setExts("feed",feed);
        eventProducer.fireEvent(eventModel);



        return WendaUtil.toJsonString(0,String.valueOf(likeService.getLikeCount(EntityType.ENTITY_COMMENT,commentId)));
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
