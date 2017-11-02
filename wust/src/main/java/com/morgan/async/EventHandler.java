package com.morgan.async;

import java.util.List;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public interface EventHandler {
    void doHandle(EventModel eventModel);
    List<EventType> getSupportEventTypes();

}
