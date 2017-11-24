package com.morgan.util;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class RedisKeyUtil {
    private static String SPLIT=":";
    private static String BIZ_LIKE="LIKE";
    private static String BIZ_DISLIKE="DISLIKE";
    private static String BIZ_EVENTQUEUE="EVENT_QUEUE";
    private static String BIZ_FOCUS="FOLOWWEE";
    private static String BIZ_FOCUSBY="FOLOWWER";
    private static String BIZ_TIMELINE="TIMELINE";







    public static String getFollowKey(int userId,int entityType){
            return BIZ_FOCUS+String.valueOf(userId)+SPLIT+entityType;
    }

    public static String getFollowByKey(int entityType,int entityId){
        return BIZ_FOCUSBY+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getLikeKey(int entityType,int entityId){
        return BIZ_LIKE+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }
    public static String getDislikeKey(int entityType,int entityId){
        return BIZ_DISLIKE+String.valueOf(entityType)+SPLIT+String.valueOf(entityId);
    }

    public static String getBizEventqueue() {
        return BIZ_EVENTQUEUE;
    }

    public static void setBizEventqueue(String bizEventqueue) {
        BIZ_EVENTQUEUE = bizEventqueue;
    }

    public static String getBizTimeline(int userId) {
        return BIZ_TIMELINE+SPLIT+String.valueOf(userId);
    }


}
