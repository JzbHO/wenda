package com.morgan.service;

import com.morgan.dao.MessageDAO;
import com.morgan.model.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */
@Service
public class MessageService {
    @Autowired
    MessageDAO messageDAO;


    public void addMessage(Message message) {
            messageDAO.addMessage(message);
    }

    public List<Message> getByConversationId(String conversationId,int offset,int num) {
        return messageDAO.getByConversationId(conversationId,0,10);
    }

    public List<Message> getAllconversation(int id, int offset, int num) {
        return messageDAO.getAllConversation(id,offset,num);
    }

    public int getUnreadCount(String convsersation_id,int id) {
        return messageDAO.getUnreadCount(convsersation_id,id);
    }

    public int getMessageCount(String convsersation_id) {
        return messageDAO.getMessageCount(convsersation_id);
    }

    public void updateHasRead(String convsersation_id,int toId) {
        messageDAO.updateHasRead(convsersation_id,toId);
    }
}
