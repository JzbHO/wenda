package com.morgan.util;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class RedisKeyUtil {
    private static String SPLIT=":";
    private static String BIZ_LIKE="LIKE";
    private static String BIZ_DISLIKE="DISLIKE";


    public static String getLikeKey(int entityType,int entityId){
        return BIZ_LIKE+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
    public static String getDislikeKey(int entityType,int entityId){
        return BIZ_DISLIKE+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }


    private static String BIZ_EVENTQUEUE="EVENT_QUEUE";

    public static String getBizEventqueue() {
        return BIZ_EVENTQUEUE;
    }

    public static void setBizEventqueue(String bizEventqueue) {
        BIZ_EVENTQUEUE = bizEventqueue;
    }
}
