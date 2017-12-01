package com.morgan.async.handler;

import com.morgan.async.EventHandler;
import com.morgan.async.EventModel;
import com.morgan.async.EventType;
import com.morgan.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
@Component
public class LikeHandler implements EventHandler {

    @Autowired
    LikeService likeService;

//    @Override
//    public void doHandle(EventModel eventModel) {
//        likeService.like(eventModel.getActorId(),eventModel.getEntityType(),eventModel.getEntityId());
//    }
    @Override
    public void doHandle(EventModel eventModel) {
        likeService.like(eventModel.getUserId(),eventModel.getEntityType(),eventModel.getEneityId());
    }

    @Override
    public List<EventType> getSupportEventTypes() {
        return Arrays.asList(EventType.LIKE);
    }
}
