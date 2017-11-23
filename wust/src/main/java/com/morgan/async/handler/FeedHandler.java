//package com.morgan.async.handler;
//
//import com.morgan.async.EventHandler;
//import com.morgan.async.EventModel;
//import com.morgan.async.EventType;
//import com.morgan.model.EntityType;
//import com.morgan.model.Feed;
//import com.morgan.service.FeedService;
//import com.morgan.service.FollowService;
//import com.morgan.util.JedisAdapter;
//import com.morgan.util.RedisKeyUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import javax.jws.Oneway;
//import java.util.Arrays;
//import java.util.List;
//
///**
// * Created by Administrator on 2017/11/7 0007.
// */
//public class FeedHandler implements EventHandler{
//    @Autowired
//    FeedService feedService;
//
//    @Autowired
//    FollowService followService;
//
//    @Autowired
//    JedisAdapter jedisAdapter;
//    @Override
//    public void doHandle(EventModel eventModel) {
//        feedService.addFeed((Feed)eventModel.getExts().get("feed"));
//        List<Integer> list=followService.getFolloweesList(eventModel.getActorId(),EntityType.ENTITY_USER,Integer.MAX_VALUE);
//        for(Integer i:list){
//            jedisAdapter.lpush(RedisKeyUtil.getBizTimeline(i),""+(((Feed) eventModel.getExts().get("feed")).getId()));
//
//        }
//    }
//
//    @Override
//    public List<EventType> getSupportEventTypes() {
//        return Arrays.asList(EventType.LIKE);
//    }
//}
