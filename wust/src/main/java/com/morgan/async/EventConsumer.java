//package com.nowcoder.async;
//
//import com.alibaba.fastjson.JSON;
//import com.morgan.controller.LoginController;
//import com.morgan.util.JedisAdapter;
//import com.morgan.util.RedisKeyUtil;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * Created by Administrator on 2017/9/29 0029.
// */
//@Component
//public class EventConsumer implements InitializingBean{
//    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EventConsumer.class);
//
//    private Map<EventType,List<EventHandler>> config=new HashMap<>();
//    private ApplicationContext applicationContext;
//
//    @Autowired
//    JedisAdapter jedisAdapter;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        Map<String,EventHandler> beans=applicationContext.getBeansOfType(EventHandler.class);
//        if(beans!=null){
//            for(Map.Entry<String,EventHandler>entry:beans.entrySet()){
//                List<EventType> eventTypes=entry.getValue().getSupportEventTypes();
//
//                for(EventType type:eventTypes){
//                    if(!config.containsKey(type)){
//                        config.put(type,new ArrayList<EventHandler>());
//                    }
//                    config.get(type).add(entry.getValue());
//                }
//            }
//        }
//
//    //    Lock lock=new ReentrantLock();
//
//        Thread thread=new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while(true){
//                    String key= RedisKeyUtil.getBizEventqueue();
//      //              lock.lock();
//                    List<String> events=jedisAdapter.brpop(0,key);
//        //            lock.unlock();
//                    for(String message:events){
//                        if(message.equals(key)){
//                            continue;
//                        }
//                        EventModel eventModel= JSON.parseObject(message,EventModel.class);
//                        if(!config.containsKey(eventModel.getType())){
//                            logger.error("无法识别的事件");
//                            continue;
//                        }
//
//                        for(EventHandler handler:config.get(eventModel.getType())){
//                            handler.doHandle(eventModel);
//                        }
//
//                    }
//                }
//            }
//        });
//        thread.start();
//
//
//
//
//    }
//}
