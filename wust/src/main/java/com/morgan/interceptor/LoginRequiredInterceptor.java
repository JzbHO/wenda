package com.morgan.interceptor;

import com.morgan.controller.LoginController;
import com.morgan.dao.LoginTicketDAO;
import com.morgan.dao.UserDAO;
import com.morgan.model.HostHolder;
import com.morgan.model.LoginTicket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
@Component
public class LoginRequiredInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoginRequiredInterceptor.class);
    @Autowired
    LoginTicketDAO loginTicketDAO;

    @Autowired
    UserDAO userDAO;


    @Autowired
    HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        logger.info("Login handerl--------");
        String loginTicket=null;
        if(httpServletRequest==null){
            logger.info("-----httpServletRequest==null-----");
            return  true;
        }
        Cookie [] cookies=httpServletRequest.getCookies();
        if(cookies==null)
            return true;
        for(Cookie cookie:cookies){
            if(cookie.getName().equals("ticket")) {
                loginTicket = cookie.getValue();
                logger.info(loginTicket+"---------");
                break;
            }
        }

        if(loginTicket!=null){
            LoginTicket logT=loginTicketDAO.selectByTicket(loginTicket);
            if(logT!=null&&logT.getStatus()==0&&logT.getExpired().after(new Date())){
                int userId=logT.getUserId();
                logger.info(userId+"userId------------");
                hostHolder.setUser(userDAO.selectUser(userId));
                logger.info(hostHolder.getUser()+"------------");
                return true;
            }
        }

        return true;

    }

    //渲染前全部加进去
    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        //modelAAndView 所有模板都可以访问
        if(modelAndView!=null) {
            modelAndView.addObject("user", hostHolder.getUser());
        }

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        hostHolder.clear();
    }
}
