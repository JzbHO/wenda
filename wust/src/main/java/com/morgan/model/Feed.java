package com.morgan.model;

import com.morgan.util.TimeLineType;

import java.util.Date;

/**
 * Created by Administrator on 2017/9/29 0029.
 */
public class Feed {
    private int id;
    private TimeLineType type;  //不同类型渲染界面不一样
    private int userId;
    private Date createDate;
    private String data;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TimeLineType getType() {
        return type;
    }

    public void setType(TimeLineType type) {
        this.type = type;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
