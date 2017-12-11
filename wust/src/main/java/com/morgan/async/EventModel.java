package com.morgan.async;


import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class EventModel{
    private EventType eventType;
    private int userId;
    private int eneityId;
    private int entityType;
    private Date date;
    private Map<String,Object> map=new HashMap<>();

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getEneityId() {
        return eneityId;
    }

    public void setEneityId(int eneityId) {
        this.eneityId = eneityId;
    }

    public int getEntityType() {
        return entityType;
    }

    public void setEntityType(int entityType) {
        this.entityType = entityType;
    }


}



//import org.apache.ibatis.ognl.ObjectElementsAccessor;
//
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * Created by Administrator on 2017/9/29 0029.
// */
//public class EventModel {
//    private EventType type;
//    private int actorId;
//    private int entityType;
//    private int entityId;
//    private Map<String,Object> exts=new HashMap<>();
//
//    public EventType getType() {
//        return type;
//    }
//
//    public EventModel setType(EventType type) {
//        this.type = type;
//        return this;
//    }
//
//    public int getActorId() {
//        return actorId;
//    }
//
//    public EventModel setActorId(int actorId) {
//        this.actorId = actorId;
//        return this;
//    }
//
//    public int getEntityType() {
//        return entityType;
//    }
//
//    public EventModel setEntityType(int entityType) {
//        this.entityType = entityType;
//        return this;
//    }
//
//    public int getEntityId() {
//        return entityId;
//    }
//
//    public EventModel setEntityId(int entityId) {
//        this.entityId = entityId;
//        return this;
//    }
//
//    public Map<String, Object> getExts() {
//        return exts;
//    }
//
//    public EventModel setExts(String key, Object value) {
//        this.exts.put(key,value);
//        return this;
//    }
//}
