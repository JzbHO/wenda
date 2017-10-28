package com.morgan.service;

import com.morgan.dao.LoginTicketDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
@Service
public class LoginTicketService {

    @Autowired
    LoginTicketDAO loginTicketDAO;

    public HashMap<String,Object> addTicket(int id){
        HashMap<String,Object> map=new HashMap<>();

        return map;
    }

}
