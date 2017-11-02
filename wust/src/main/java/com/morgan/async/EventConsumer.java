package com.morgan.async;

import com.alibaba.fastjson.JSON;
import com.morgan.util.JedisAdapter;
import com.morgan.util.RedisKeyUtil;
import jdk.nashorn.internal.parser.JSONParser;
import org.slf4j.LoggerFactory;
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

/**
 * Created by Administrator on 2017/9/29 0029.
 */
@Component
public class EventConsumer implements InitializingBean,ApplicationContextAware{
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(EventConsumer.class);

    private Map<EventType,List<EventHandler>> config=new HashMap<>();
    private ApplicationContext applicationContext;

    @Autowired
    JedisAdapter jedisAdapter;

    @Override
    public void afterPropertiesSet() throws Exception {
            Map<String,EventHandler> eventMap=applicationContext.getBeansOfType(EventHandler.class);
            if(eventMap!=null) {
                for (Map.Entry<String, EventHandler> entry : eventMap.entrySet()) {

                    for (EventType type : entry.getValue().getSupportEventTypes()) {
                        if (config.containsKey(type)) {
                            config.get(type).add(entry.getValue());
                        } else {
                            List<EventHandler> list = new ArrayList<>();
                            list.add(entry.getValue());
                            config.put(type, list);
                        }

                    }
                }
            }

            Thread thread=new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        List<String> list=jedisAdapter.brpop(0,RedisKeyUtil.getBizEventqueue());
                        EventModel model= JSON.parseObject(list.get(1),EventModel.class);
                        if (!config.containsKey(model.getType())) {
                            logger.error("不能识别的事件");
                            continue;
                        }
                        for(EventHandler handler:config.get(model.getType())){
                            handler.doHandle(model);
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
