package com.morgan.service;

import com.morgan.dao.LoginLogDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by Administrator on 2017/11/23 0023.
 */
@Service
public class LogService {
    @Autowired
    LoginLogDAO loginLogDAO;

    public void addLoginLog(int userId){
        loginLogDAO.addLoginLog(new Date(),userId);
    }


}
