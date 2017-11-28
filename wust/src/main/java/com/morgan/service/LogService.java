package com.morgan.service;

import com.morgan.dao.LoginLogDAO;
import com.morgan.model.LoginLog;
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
        LoginLog loginLog=new LoginLog();
        loginLog.setLoginDate(new Date());
        loginLog.setUserId(userId);
        loginLogDAO.addLoginLog(loginLog);
    }


}
