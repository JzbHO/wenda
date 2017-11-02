package com.morgan.async;

import org.apache.ibatis.ognl.ObjectElementsAccessor;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class EventModel {
    private EventType type;
    private int actorId;
    private int entityType;
    private int entityId;
    private Map<String,Object> exts=new HashMap<>();

    public EventType getType() {
        return type;
    }

    public EventModel setType(EventType type) {
        this.type = type;
        return this;
    }

    public int getActorId() {
        return actorId;
    }

    public EventModel setActorId(int actorId) {
        this.actorId = actorId;
        return this;
    }

    public int getEntityType() {
        return entityType;
    }

    public EventModel setEntityType(int entityType) {
        this.entityType = entityType;
        return this;
    }

    public int getEntityId() {
        return entityId;
    }

    public EventModel setEntityId(int entityId) {
        this.entityId = entityId;
        return this;
    }

    public Map<String, Object> getExts() {
        return exts;
    }

    public EventModel setExts(String key, Object value) {
        this.exts.put(key,value);
        return this;
    }
}
