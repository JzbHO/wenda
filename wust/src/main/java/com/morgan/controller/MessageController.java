package com.morgan.controller;

import com.morgan.model.*;
import com.morgan.service.MessageService;
import com.morgan.service.MySensitiveService;
import com.morgan.service.QuestionService;
import com.morgan.service.UserService;
import com.morgan.util.*;
import com.morgan.util.ViewObject;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.HtmlUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/10/30 0030.
 */
@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    MySensitiveService mySensitiveService;

    @Autowired
    HostHolder hostHolder;

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @RequestMapping(path={"/msg/addMessage"},method = {RequestMethod.POST})
    @ResponseBody
    public String addMessage(@RequestParam("toName") String toName,
                             @RequestParam("content") String content){
        User user=hostHolder.getUser();
        if(user==null){
            return WendaUtil.toJsonString(999,"用户未登录");
        }
        Message message=new Message();


        content=mySensitiveService.filter(HtmlUtils.htmlEscape(content));
        message.setContent(content);
        message.setFromId(user.getId());
        message.setToId(userService.getByName(toName).getId());
        message.setConversationId();
        message.setCreatedDate(new Date());
        message.setHasRead(WendaUtil.COMMENT_NOREAD);

        messageService.addMessage(message);
        return WendaUtil.toJsonString(0);
    }

    @RequestMapping(path={"/msg/detail"},method = {RequestMethod.GET})
    public String msg(Model model, @RequestParam("conversationId") String conversationId) {
        List<ViewObject> messages=new ArrayList<>();
                List<Message> message=messageService.getByConversationId(conversationId,0,10);
                if(message!=null) {
                    for(Message message1:message) {
                        ViewObject vo = new ViewObject();
                        User user = userService.getUser(message.get(0).getFromId());
                        vo.set("headUrl", user.getHeadUrl());
                        vo.set("userId", user.getId());
                        vo.set("message",message1);
                        messages.add(vo);
                    }
                }
                model.addAttribute("messages",messages);
        return "letterDetail";
    }

    @RequestMapping(path={"/msg/list"},method = {RequestMethod.GET})
    public String list(Model model) {
        try {
            int localUserId = hostHolder.getUser().getId();
            List<ViewObject> conversations = new ArrayList<ViewObject>();
            List<Message> conversationList = messageService.getAllconversation(localUserId, 0, 10);
            for (Message msg : conversationList) {
                ViewObject vo = new ViewObject();
                vo.set("conversation", msg);
                int targetId = msg.getFromId();
                User user = userService.getUser(targetId);
                vo.set("user", user);
                vo.set("unread",msg.getHasRead());
                conversations.add(vo);
            }
            model.addAttribute("conversations", conversations);
        } catch (Exception e) {
            logger.error("获取站内信列表失败" + e.getMessage());
        }
        return "letter";
    }





}
