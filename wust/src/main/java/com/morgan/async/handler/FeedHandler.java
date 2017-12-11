package com.morgan.async.handler;

import com.alibaba.fastjson.JSONObject;
import com.morgan.async.EventHandler;
import com.morgan.async.EventModel;
import com.morgan.async.EventType;
import com.morgan.controller.HomeController;
import com.morgan.model.EntityType;
import com.morgan.model.Feed;
import com.morgan.model.HostHolder;
import com.morgan.model.User;
import com.morgan.service.FeedService;
import com.morgan.service.QuestionService;
import com.morgan.service.UserService;
import com.morgan.util.TimeLineType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/12/10 0010.
 */
@Component
public class FeedHandler implements EventHandler {



    @Autowired
    UserService userService;

    @Autowired
    QuestionService questionService;

    @Autowired
    FeedService feedService;

    private static final Logger logger= LoggerFactory.getLogger(FeedHandler.class);
    @Override
    public void doHandle(EventModel eventModel) {
        Feed feed=new Feed();
        feed.setUserId(eventModel.getUserId());
        feed.setCreateDate(eventModel.getDate());
        User user= JSONObject.parseObject( JSONObject.toJSONString(eventModel.getMap().get("user")),User.class);
        if(eventModel.getEventType()==EventType.FOLLOW) {
            if (eventModel.getEntityType() == EntityType.ENTITY_QUESTION) {
                feed.setType(TimeLineType.FOCUS);
                questionService.getById(eventModel.getEneityId());
                logger.info(""+eventModel.getUserId());
                feed.setData(user.getName() + "关注了 " + questionService.getById(eventModel.getEneityId()).getTitle() + "问题");
                feedService.addFeed(feed);

            }
            if (eventModel.getEntityType() == EntityType.ENTITY_USER) {
                feed.setType(TimeLineType.FOCUS);
                feed.setData(user.getName()  + "关注了 " + userService.getUser(eventModel.getEneityId()).getName() + "用户");
                feedService.addFeed(feed);
            }
        }
        if(eventModel.getEventType()==EventType.LIKE){
            feed.setType(TimeLineType.LIKE);
            feed.setData(user.getName() +"点赞了在"+eventModel.getMap().get("questionTitle")+"问题下用户"+eventModel.getMap().get("userName")+"的评论");
            feedService.addFeed(feed);
        }

    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.FOLLOW,EventType.LIKE);
    }
}
