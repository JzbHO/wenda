package com.morgan.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.morgan.model.*;
import com.morgan.model.ViewObject;
import com.morgan.service.*;

import com.morgan.util.*;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@Controller
public class HomeController {

    private static final Logger logger=LoggerFactory.getLogger(HomeController.class);

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

    @Autowired
    HostHolder hostHolder;

    @Autowired
    CommentService commentService;




    @RequestMapping(path={"/index1/{userId}"},method = {RequestMethod.GET})
    public String index1(Model model, @PathVariable("userId") int userId){
        List<Object> vos=new ArrayList<Object>();
        //是否时活跃用户
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
        return "index";
    }


    @RequestMapping(path={"/index","/"},method = {RequestMethod.GET,RequestMethod.POST})
    public String index(Model model){
        List<ViewObject> vos;
        vos = getLatestQuestions(85212, 0, 10);
        model.addAttribute("vos", vos);
        return "index";
    }

    @RequestMapping(path={"/user/{userId}"},method = {RequestMethod.GET})
    public String personCenter(Model model,@PathVariable("userId") int userId){
        ViewObject vo=new ViewObject();
        vo.set("user",userService.getUser(userId));
        //粉丝数量
        vo.set("followeeCount",followService.getFolloweeCount(EntityType.ENTITY_USER,userId));
        //关注数量
        vo.set("followerCount",followService.getFollowerCount(userId,EntityType.ENTITY_USER));
        vo.set("commentCount",commentService.getUserCommentCount(userId));
        vo.set("followed",followService.isFollow(hostHolder.getUser().getId(),EntityType.ENTITY_USER,userId));
        model.addAttribute("profileUser",vo);
        return "profile";
    }




    @RequestMapping(path={"/getQuestions"},method = {RequestMethod.POST,RequestMethod.GET} )
    @ResponseBody
    public String getQuestion(@RequestParam("beginId") int beginId){
        List<TempQuestion> list=getLatestQuestionsTem(beginId,beginId,2);
        String s= JSON.toJSONString(list);
        return s;
    }

    private List<TempQuestion> getLatestQuestionsTem(int id,int offset,int num){
        List<Question> list=questionService.getLatestQuestions(id,offset,num);
        List<TempQuestion> vos=new ArrayList<TempQuestion>();
        for(Question question:list){
            TempQuestion tempQuestion=new TempQuestion();
            tempQuestion.setQuestion(question);
            tempQuestion.setUser(userService.getUser(question.getUserId()));
            tempQuestion.setFollowCount(followService.getFolloweeCount(EntityType.ENTITY_QUESTION,question.getId()));
            vos.add(tempQuestion);
        }
        return vos;
    }

    private List<ViewObject> getLatestQuestions(int id,int offset,int num){
        List<Question> list=questionService.getLatestQuestions(id,0,num);
        List<ViewObject> vos=new ArrayList<ViewObject>();
        for(Question question:list){
            ViewObject vo=new ViewObject();
            vo.set("question",question);
            vo.set("user",userService.getUser(question.getUserId()));
            vo.set("followCount",followService.getFolloweeCount(EntityType.ENTITY_QUESTION,question.getId()));
            vos.add(vo);
        }
        return vos;
    }




}
