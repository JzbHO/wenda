package com.morgan.service;

import com.morgan.util.JedisAdapter;
import com.morgan.util.RedisKeyUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2017/9/4 0004.
 */
@Service
public class LikeService {
    @Autowired
    JedisAdapter jedisAdapter;

    public long getLikeCount(int entityType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);
        return jedisAdapter.scard(likeKey);
    }


    public int getLikeStatus(int userId,int entityType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);
        if(jedisAdapter.sismemebr(likeKey,String.valueOf(userId))){
            return 1;
        }
        String dislikeKey= RedisKeyUtil.getDislikeKey(entityType,entityId);
        return jedisAdapter.sismemebr(dislikeKey,String.valueOf(userId))?-1:0;
    }

    public long like(int userId,int entityType,int entityId){
        String likeKey= RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.add(likeKey,String.valueOf(userId));

        String dislikeKey=RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.srem(dislikeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);

    }

    public long dislike(int userId,int entityType,int entityId){
        String dislikeKey= RedisKeyUtil.getDislikeKey(entityType,entityId);
        jedisAdapter.add(dislikeKey,String.valueOf(userId));

        String likeKey=RedisKeyUtil.getLikeKey(entityType,entityId);
        jedisAdapter.srem(likeKey,String.valueOf(userId));
        return jedisAdapter.scard(likeKey);

    }



}
