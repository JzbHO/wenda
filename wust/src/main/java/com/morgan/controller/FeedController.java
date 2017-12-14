package com.morgan.controller;

import com.morgan.model.EntityType;
import com.morgan.model.Feed;
import com.morgan.model.HostHolder;
import com.morgan.model.ViewObject;
import com.morgan.service.FeedService;
import com.morgan.service.FollowService;
import com.morgan.service.UserService;
import com.morgan.util.JedisAdapter;
import com.morgan.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/11/7 0007.
 */
@Controller
public class FeedController {
    @Autowired
    FeedService feedService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    FollowService followService;

    @Autowired
    UserService userService;
    @Autowired
    JedisAdapter jedisAdapter;
    @RequestMapping(path={"/pullfeeds"},method = {RequestMethod.GET,RequestMethod.POST})
    public String getPullFeeds(Model model){
        int localUserId=0;
        if(hostHolder.getUser()!=null){
            localUserId=hostHolder.getUser().getId();
        }
        List<Integer> array=followService.getFollowList(localUserId,EntityType.ENTITY_USER,Integer.MAX_VALUE);
        List<Feed> feeds=feedService.getUserFeeds(0,array,10);
        List<ViewObject> vos=new ArrayList<>();
        for(Feed feed:feeds){
            ViewObject vo=new ViewObject();
            vo.set("user",userService.getUser(feed.getUserId()));
            vo.set("feed",feed);
            vos.add(vo);
        }
        model.addAttribute("feeds",vos);
        return "feeds";
    }

    @RequestMapping(path={"/pushfeeds"},method = {RequestMethod.GET,RequestMethod.POST})
    public String getPushFeeds(Model model){
        int localUserId=hostHolder!=null?hostHolder.getUser().getId():0;
        String key= RedisKeyUtil.getBizTimeline(localUserId);
        List<String> list=jedisAdapter.lrange(key,0,Integer.MAX_VALUE);
        List<Feed> feeds=new ArrayList<>();
        for (String s:list){
            Feed feed=feedService.geFeedById(Integer.parseInt(s));
            if(feed!=null){
                feeds.add(feed);
            }
        }
        model.addAttribute("feeds",feeds);
        return "feeds";
   }



}
