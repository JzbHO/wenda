package com.morgan.controller;

import com.alibaba.fastjson.JSONObject;
import com.morgan.model.EntityType;
import com.morgan.model.Feed;
import com.morgan.model.Question;
import com.morgan.model.ViewObject;
import com.morgan.service.FeedService;
import com.morgan.service.FollowService;
import com.morgan.service.QuestionService;

import com.morgan.service.UserService;
import com.morgan.util.JedisAdapter;
import com.morgan.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@Controller
public class HomeController {
    @Autowired
    QuestionService questionService;

    @Autowired
    UserService userService;

    @Autowired
    FeedService feedService;

    @Autowired
    JedisAdapter jedisAdapter;

    @Autowired
    FollowService followService;


    @RequestMapping(path={"/index1/{userId}"},method = {RequestMethod.GET})
    public String index1(Model model, @PathVariable("userId") int userId){
        List<Object> vos=new ArrayList<Object>();
        if(userService.isActivity(userId)){
            //push新消息
            List<String >list=null;
            for(int i=0;i<10;i++){
                list=jedisAdapter.brpop(0,RedisKeyUtil.getBizTimeline(userId));
                if(list==null)
                    break;
                JSONObject vo=JSONObject.parseObject(list.get(1));
                vos.add(vo);
            }
        }else {
            //pull新消息,count 待传值
            List<Integer> userIds=followService.getFollowList(userId, EntityType.ENTITY_USER,Integer.MAX_VALUE);
            List<Feed> list=feedService.getUserFeeds(Integer.MAX_VALUE,userIds,10);
           for(Feed feed:list){
               vos.add(feed);
           }
        }
        model.addAttribute("vos",vos);
        return "redirect:/";
    }

    @RequestMapping(path={"/index","/"},method = {RequestMethod.GET})
    public String index(Model model){
        List<ViewObject> vos=getLatestQuestions(3,0,5);
        model.addAttribute("vos",vos);
        return "index";
    }



    private List<ViewObject> getLatestQuestions(int id,int offset,int num){
        List<Question> list=questionService.getLatestQuestions(id,0,10);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        for(Question question:list){
            ViewObject vo=new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vos.add(vo);
        }
        return vos;
    }




}
