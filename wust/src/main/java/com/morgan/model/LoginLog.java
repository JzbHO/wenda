package com.morgan.model;

import java.util.Date;

/**
 * Created by Administrator on 2017/11/23 0023.
 */
public class LoginLog {
    private int id;
    private int userId;
    private Date loginDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }
}
