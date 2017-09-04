package com.nowcoder.controller;

import com.nowcoder.model.HostHolder;
import com.nowcoder.model.Message;
import com.nowcoder.model.ViewObject;
import com.nowcoder.service.MessageService;
import com.nowcoder.service.UserService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.jws.WebParam;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */
@Controller
public class MessageController {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MessageService messageService;

    @Autowired
    UserService userService;

    @Autowired
    HostHolder hostHolder;



    @RequestMapping(path={"/msg/list"},method = {RequestMethod.GET})
    public  String getConversationList(Model model){
        if(hostHolder.getUser()==null){
            return "redirect:/reglogin";
        }
        int localUserId=hostHolder.getUser().getId();
        List<Message> conversationList =messageService.getConversationList(localUserId,0,10);
        List<ViewObject> conversations=new ArrayList<ViewObject>();
        for(Message message:conversationList){
            ViewObject vo=new ViewObject();
            vo.set("conversation",message);
            int targetId=message.getFromId()==localUserId?message.getToId():message.getFromId();
            vo.set("user",userService.getUser(targetId));
        }
        model.addAttribute("conversations",conversationList);
         return "letter";
    }


    @RequestMapping(path = {"/msg/datail"},method = {RequestMethod.GET})
    public String getConversationDetail(Model model, @RequestParam("conversationId") String conversationId){
        try{
            List<Message> messageList=messageService.getConversationDetail(conversationId,0,10);
            List<ViewObject> messages=new ArrayList<ViewObject>();
            for(Message message:messageList){
                ViewObject vo=new ViewObject();
                vo.set("message",message);
                vo.set("user",userService.getUser(message.getFromId()));
                messages.add(vo);
            }
            model.addAttribute("messages",messages);
        }catch (Exception e){
            logger.error("获取详情失败"+e.getMessage());
        }
     return "letterDetail";
    }


}
