package com.morgan.service;

import com.morgan.util.JedisAdapter;
import com.morgan.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DaoSupport;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Transaction;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by Administrator on 2017/10/8 0008.
 */
@Service
public class FollowService {
    @Autowired
    JedisAdapter jedisAdapter;


    public boolean follow(int userId,int entityType,int entityId){
        Date date=new Date();
        String follwekey=RedisKeyUtil.getFollowKey(userId,entityType);
        String follweBykey=RedisKeyUtil.getFollowByKey(entityType,entityId);

        Jedis jedis=jedisAdapter.getResource();
        Transaction tra=jedis.multi();

        tra.zadd(follwekey,date.getTime(),String.valueOf(entityId));
        tra.zadd(follweBykey,date.getTime(),String.valueOf(userId));
        List<Object> list=jedisAdapter.exec(tra,jedis);
        return list.size()==2&&(Long)list.get(0)>0&&(Long)list.get(1)>0;
    }
    public boolean unfollow(int userId,int entityType,int entityId){
        Date date=new Date();
        String follwekey=RedisKeyUtil.getFollowKey(userId,entityType);
        String follweBykey=RedisKeyUtil.getFollowByKey(entityType,entityId);

        Jedis jedis=jedisAdapter.getResource();
        Transaction tra=jedis.multi();

        tra.zrem(follwekey,String.valueOf(entityId));
        tra.zrem(follweBykey,String.valueOf(userId));
        List<Object> list=jedisAdapter.exec(tra,jedis);
        return list.size()==2&&(Long)list.get(0)>0&&(Long)list.get(1)>0;
    }


    public static ArrayList<Integer> trans(Set<String>set){
        ArrayList<Integer> list=new ArrayList<>();
        for(String str:set){
            list.add(Integer.parseInt(str));
        }
    return list;
}
    //某用户关注的实体
    public ArrayList<Integer> getFollowList(int userId,int entityType,int offset,int count){
        String follwekey=RedisKeyUtil.getFollowKey(userId,entityType);
        return trans(jedisAdapter.zrevrange(follwekey,offset,count));
    }
    public ArrayList<Integer> getFollowList(int userId,int entityType,int count){
        String follwekey=RedisKeyUtil.getFollowKey(userId,entityType);
        return trans(jedisAdapter.zrevrange(follwekey,0,count));
    }

    //某实体的粉丝
    public ArrayList<Integer> getFolloweesList(int userId,int entityType,int offset,int count){
        String follweekey=RedisKeyUtil.getFollowByKey(entityType,userId);
        return trans(jedisAdapter.zrevrange(follweekey,offset,count));
    }
    public ArrayList<Integer> getFolloweesList(int userId,int entityType,int count){
        String follwekeey=RedisKeyUtil.getFollowKey(entityType,userId);
        return trans(jedisAdapter.zrevrange(follwekeey,0,count));
    }

    public Long getFollowCount(int userId,int entityType){
        String follwekey=RedisKeyUtil.getFollowKey(userId,entityType);
        return jedisAdapter.zcard(follwekey);
    }

    public boolean isFollow(int userId,int entityType,int entityId){
        String follwedkey=RedisKeyUtil.getFollowByKey(entityId,entityType);
        return jedisAdapter.zscore(follwedkey,String.valueOf(userId))!=null;
    }










}
