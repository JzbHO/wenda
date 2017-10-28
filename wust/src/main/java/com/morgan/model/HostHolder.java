package com.morgan.model;

import org.springframework.stereotype.Component;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
//当前用户
@Component
public class HostHolder {
    //定义整个上下文的变量
    private static ThreadLocal<User> users=new ThreadLocal<User>();


    public User getUser(){
        return users.get();
    }
    public void setUser(User user){
        users.set(user);
    }
    public void clear(){
        users.remove();
    }

}
