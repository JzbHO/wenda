package com.nowcoder.service;

import com.nowcoder.dao.MessageDAO;
import com.nowcoder.model.Message;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */
public class MessageService {
    @Autowired
    MessageDAO messageDAO;



    public List<Message> getConversationDetail(String conversatonid,int num,int limit){
        return messageDAO.getConversationDetail(conversatonid,num,limit);

    }

    public List<Message> getConversationList(int Userid,int num,int limit){
        return messageDAO.getConversationList(Userid,num,limit);

    }


    public void addMessage(Message message) {
    }
}
