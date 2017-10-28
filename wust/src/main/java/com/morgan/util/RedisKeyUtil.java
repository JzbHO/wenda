package com.morgan.util;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class RedisKeyUtil {
    private static String BIZ_EVENTQUEUE="EVENT_QUEUE";

    public static String getBizEventqueue() {
        return BIZ_EVENTQUEUE;
    }

    public static void setBizEventqueue(String bizEventqueue) {
        BIZ_EVENTQUEUE = bizEventqueue;
    }
}
