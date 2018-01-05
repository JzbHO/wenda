package com.morgan.model;

import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
//当前用户
@Component
public class HostHolder {
    //定义整个上下文的变量
    //刷新页面 也是新建一个request(Thread)
    private static ThreadLocal<User> users=new ThreadLocal<User>();
    private static ThreadLocal<Integer> questionsum=new ThreadLocal<>();

    public Integer getSum(){return questionsum.get();}
    public void setSum(Integer sum){questionsum.set(sum);}



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
