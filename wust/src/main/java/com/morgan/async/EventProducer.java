package com.morgan.async;

import com.alibaba.fastjson.JSONObject;
import com.morgan.util.JedisAdapter;
import com.morgan.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

@Service
public class EventProducer{
    @Autowired
    JedisAdapter jedisAdapter;

    public void pushEvent(EventModel eventModel){
        String s= JSONObject.toJSONString(eventModel);
        jedisAdapter.lpush(RedisKeyUtil.getBizEventqueue(),s);
        return ;
    }




}


//import com.alibaba.fastjson.JSONObject;
//import com.morgan.util.JedisAdapter;
//import com.morgan.util.RedisKeyUtil;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * Created by Administrator on 2017/9/29 0029.
// */
//@Service
//public class EventProducer {
//    @Autowired
//    JedisAdapter jedisAdapter;
//
//    public boolean fireEvent(EventModel eventModel){
//        try{
//            String json= JSONObject.toJSONString(eventModel);
//            String key= RedisKeyUtil.getBizEventqueue();
//            jedisAdapter.lpush(key,json);
//            return true;
//        }catch (Exception e){
//            return false;
//        }
//    }
//
//
//}
