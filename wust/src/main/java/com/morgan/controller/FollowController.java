package com.morgan.controller;

import com.morgan.model.EntityType;
import com.morgan.model.HostHolder;
import com.morgan.service.FollowService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @RequestMapping(path ={"/followQuestion"},method = {RequestMethod.POST,RequestMethod.GET} )
    @ResponseBody
    public String followQusetion(@RequestParam("questionId") int questionId) {
        logger.info("收到问题编号---"+followService.getFolloweeCount(EntityType.ENTITY_QUESTION,questionId));
        followService.follow(hostHolder.getUser().getId(), EntityType.ENTITY_QUESTION,questionId);
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


}
