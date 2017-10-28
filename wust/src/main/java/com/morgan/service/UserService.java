package com.morgan.service;

import com.morgan.dao.LoginTicketDAO;
import com.morgan.dao.UserDAO;
import com.morgan.model.LoginTicket;
import com.morgan.model.User;
import com.morgan.util.WendaUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by Administrator on 2017/10/26 0026.
 */
@Service
public class UserService {


    @Autowired
    UserDAO userDAO;

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public User getUser(int id){
        return userDAO.selectUser(id);
    }

    public User getByName(String name){
        return userDAO.selectByName(name);
    }


    public Map addUser(String username, String password) {
        HashMap<String,Object> map=new HashMap<>();
        if(StringUtils.isBlank(username)){
            map.put("msg","用户名不能为空");
            return  map;
        }
        if(StringUtils.isBlank(password)){
            map.put("msg","密码不能为空");
            return  map;
        }
        User user=userDAO.selectByName(username);
        if(user!=null){
            map.put("msg","用户名已存在");
            return  map;
        }
        Random random=new Random();
        String salt= UUID.randomUUID().toString().substring(0,5);
        user=new User();
        user.setName(username);
        user.setSalt(salt);
        user.setPassword(WendaUtil.MD5(password+salt));
        user.setHeadUrl(String.format("http://images.nowcoder.com/head/%dt.png",random.nextInt(1000)));
        userDAO.addUser(user);

        map.put("ticket",addTicket(username).getTicket());
        return map;
    }




    public Map<String,Object> checkCount(String username, String password) {
        HashMap<String,Object> map=new HashMap<>();
        User user=userDAO.selectByName(username);
        if(null==user){
            map.put("msg","用户名不存在");
        }else {
            if(!WendaUtil.MD5(password+user.getSalt()).equals(user.getPassword())) {
                map.put("msg", "密码错误");
            }
        }
        map.put("ticket",addTicket(username).getTicket());
        return  map;
    }

    private LoginTicket addTicket(String username){
        LoginTicket loginTicket=new LoginTicket();
        loginTicket.setTicket(UUID.randomUUID().toString().replace("-",""));
        loginTicket.setExpired(new Date(3600*24*30+new Date().getTime()));
        loginTicket.setStatus(0);
        loginTicket.setUserId(userDAO.selectByName(username).getId());

        loginTicketDAO.addTicket(loginTicket);
        return loginTicket;
    }

    public void logOut(String ticket) {
        loginTicketDAO.updateStatus(ticket,1);
    }
}
