//package com.nowcoder.async.handler;
//
//import com.morgan.async.EventHandler;
//import com.morgan.async.EventModel;
//import com.morgan.async.EventType;
//import com.morgan.model.Message;
//import com.morgan.model.User;
//import com.morgan.service.MessageService;
//import com.morgan.service.UserService;
//import com.morgan.util.WendaUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Date;
//import java.util.List;
//
///**
// * Created by Administrator on 2017/9/29 0029.
// */
//@Component
//public class LikeHandler implements EventHandler {
//    @Autowired
//    MessageService messageService;
//
//    @Autowired
//    UserService userService;
//
//
//
//    @Override
//    public void doHandle(EventModel eventModel) {
//        Message message=new Message();
//        message.setFromId(WendaUtil.SYSTEM_USER);
//        message.setToId(eventModel.getEntityOwnerId());
//        message.setCreatedDate(new Date());
//        User user=userService.getUser(eventModel.getActorId());
//        message.setContent("用户"+user.getName()+"赞了你的评论");
//        messageService.addMessage(message);
//    }
//
//    @Override
//    public List<EventType> getSupportEventTypes() {
//        return null;
//    }
//}
