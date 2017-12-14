package com.morgan.controller;

import com.morgan.async.EventModel;
import com.morgan.async.EventProducer;
import com.morgan.async.EventType;
import com.morgan.model.EntityType;
import com.morgan.model.HostHolder;
import com.morgan.service.FollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by Administrator on 2017/12/7 0007.
 */
@Controller
public class FollowController {
    private static final Logger logger= LoggerFactory.getLogger(FollowController.class);

    @Autowired
    FollowService followService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    EventProducer eventProducer;

    @RequestMapping(path ={"/followQuestion"},method = {RequestMethod.POST,RequestMethod.GET} )
    @ResponseBody
    public String followQusetion(@RequestParam("questionId") int questionId) {
        logger.info("收到问题编号---"+followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));
        followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION,questionId);
        HashMap<String,Object> map=new HashMap<>();
        map.put("user",hostHolder.getUser());
        EventModel eventModel=new EventModel();
        eventModel.setEventType(EventType.FOLLOW);
        eventModel.setUserId(hostHolder.getUser().getId());
        eventModel.setMap(map);
        eventModel.setEneityId(questionId);
        eventModel.setEntityType(EntityType.ENTITY_QUESTION);
        eventModel.setDate(new Date());
        eventProducer.pushEvent(eventModel);
        logger.info("收到问题编号---"+followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));
        Long size=followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId);
        return  ""+size;
    }
    @RequestMapping(path ={"/unfollowQuestion"},method = {RequestMethod.POST,RequestMethod.GET} )
    @ResponseBody
    public String unfollowQusetion(@RequestParam("questionId") int questionId) {
        logger.info("收到问题编号---"+followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));
        followService.unfollow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION,questionId);
        logger.info("收到问题编号---"+followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));
        Long size=followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId);
        return  ""+size;
    }

    @RequestMapping(path={"/followUser"},method = {RequestMethod.POST,RequestMethod.GET} )
    @ResponseBody
    public String followUser(@RequestParam("userId") int userId) {
        logger.info("关注服务到达---");
        followService.follow(hostHolder.getUser().getId(),EntityType.ENTITY_USER,userId);
        return followService.getFolloweeCount(EntityType.ENTITY_USER,userId)+"";
    }

    @RequestMapping(path ={"/unfollowUser"},method = {RequestMethod.POST,RequestMethod.GET} )
    @ResponseBody
    public String unfollowUser(@RequestParam("userId") int userId) {
        logger.info("关注服务到达---");
        followService.unfollow(hostHolder.getUser().getId(),EntityType.ENTITY_USER,userId);
        return followService.getFolloweeCount(EntityType.ENTITY_USER,userId)+"";
    }





}
