package com.morgan.async;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.morgan.util.JedisAdapter;
import com.morgan.util.RedisKeyUtil;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

@Component
public class EventConsumer implements InitializingBean,ApplicationContextAware{
    @Autowired
    JedisAdapter jedisAdapter;

    ApplicationContext applicationContext;

    private Map<EventType,List<EventHandler>> config=new HashMap<EventType,List<EventHandler>>();

    @Override
    public void afterPropertiesSet() throws Exception {
        Map<String,EventHandler> map=applicationContext.getBeansOfType(EventHandler.class);

        for(Map.Entry<String,EventHandler> handMap :map.entrySet()){
            List<EventType> list=handMap.getValue().getSupportEventTypes();
            for(EventType type :list) {
                if (!config.containsKey(type)){
                    List <EventHandler> temp=new ArrayList();
                    temp.add(handMap.getValue());
                    config.put(type,temp);
                }else {
                    config.get(type).add(handMap.getValue());
                }
          }
        }
        Thread thread=new Thread(new Runnable() {
            @Override
            public void run() {
                while(true){
                    List<String> list=jedisAdapter.brpop(0,RedisKeyUtil.getBizEventqueue());

                        EventModel model= (EventModel) JSONObject.parse(list.get(1));
                        List<EventHandler> handlers=config.get(model.getEventType());
                        for(EventHandler hand:handlers){
                            hand.doHandle(model);
                        }

                }
            }
        });
        thread.start();



    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}



//
//@Component
//public class EventConsumer implements InitializingBean,ApplicationContextAware{
//    //private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EventConsumer.class);
//
//    private Map<EventType,List<EventHandler>> config=new HashMap<>();
//    private ApplicationContext applicationContext;
//
//    @Autowired
//    JedisAdapter jedisAdapter;
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//            Map<String,EventHandler> eventMap=applicationContext.getBeansOfType(EventHandler.class);
//            if(eventMap!=null) {
//                for (Map.Entry<String, EventHandler> entry : eventMap.entrySet()) {
//
//                    for (EventType type : entry.getValue().getSupportEventTypes()) {
//                        if (config.containsKey(type)) {
//                            config.get(type).add(entry.getValue());
//                        } else {
//                            List<EventHandler> list = new ArrayList<>();
//                            list.add(entry.getValue());
//                            config.put(type, list);
//                        }
//
//                    }
//                }
//            }
//
//            Thread thread=new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    while (true) {
//                        List<String> list=jedisAdapter.brpop(0,RedisKeyUtil.getBizEventqueue());
//                        EventModel model= JSON.parseObject(list.get(1),EventModel.class);
//                        if (!config.containsKey(model.getEventType())) {
//                           // logger.error("不能识别的事件");
//                            continue;
//                        }
//                        for(EventHandler handler:config.get(model.getEventType())){
//                            handler.doHandle(model);
//                        }
//
//                    }
//
//                }
//            });
//            thread.start();
//
//
//
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext=applicationContext;
//    }
//}
