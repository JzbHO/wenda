package com.morgan.controller;

import com.morgan.service.LoginTicketService;
import com.morgan.service.UserService;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/26 0026.
 */
@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    LoginTicketService loginTicketService;

    @RequestMapping(path={"/reglogin"},method = {RequestMethod.GET})
    public String reg(Model model,
                      @RequestParam(value="next",required = false) String next ) {
        model.addAttribute("next",next);
        return "login";
    }


    @RequestMapping(path={"/reg/"},method = {RequestMethod.POST})
    public String reg(HttpServletResponse response, Model model,
                      @RequestParam("username") String username,
                      @RequestParam("password") String password){

        Map<String ,Object>map= userService.addUser(username,password);
     //   try{
            if(map.containsKey("msg")){
                model.addAttribute("msg",map.get("msg"));
                return "login";
            }else {
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                return "index";
            }
//        }catch (Exception e ){
//            logger.error("注册异常"+e.getMessage());
//            return "login";
//        }
    }

    @RequestMapping(path={"/login/"},method = {RequestMethod.POST})
    public String login(HttpServletResponse response,Model model,
                        @RequestParam("next") String next,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password){

        Map<String ,Object>map= userService.checkCount(username,password);
       // try{
            if(map.containsKey("msg")){
                model.addAttribute("msg",map.get("msg"));
                return "login";

            }else {
                Cookie cookie = new Cookie("ticket",map.get("ticket").toString());
                cookie.setPath("/");
                response.addCookie(cookie);
                if(next!=null){
                    return "redirect:/";
                }else {
                    return "redirect:/";
                }
            }
//        }catch (Exception e ){
//            logger.error("登录异常"+e.getMessage());
//            return "login";
//        }
    }

    @RequestMapping(path={"/logout"},method = {RequestMethod.GET})
    public String logout(@CookieValue("ticket") String ticket){
        userService.logOut(ticket);
        return "redirect:/";
//        }catch (Exception e ){
//            logger.error("登录异常"+e.getMessage());
//            return "login";
//        }
    }





}
