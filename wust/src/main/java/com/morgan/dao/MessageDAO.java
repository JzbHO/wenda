package com.morgan.dao;

import com.morgan.model.Message;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */
@Mapper
public interface MessageDAO {
    String TABLE_NAME = " message ";
    String INSERT_FIELDS = " from_id, to_id, content, has_read, conversation_id, created_date ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME,"(", INSERT_FIELDS ,") values (#{fromId},#{toId},#{content},#{hasRead},#{conversationId},#{createdDate})"})
    void addMessage(Message message);

    @Select({"select "+SELECT_FIELDS+ " from "+TABLE_NAME+"where conversation_id=#{conversationId} order by id desc limit #{offset}, #{limit}"})
    List<Message> getByConversationId(@Param("conversationId") String conversationId,
                                      @Param("offset") int offset,
                                      @Param("limit") int limit);

    //select * ,count(id) as cnt from (select *from message where to_id=20 order by created_date desc)ll group by from_id;
//    @Select({"select "+INSERT_FIELDS+ ",count(id) as id from "+" (select * from message where to_id=#{id} order by created_date desc)"+
//            "ll group by from_id limit #{offset}, #{num}"})
    @Select({"select "+INSERT_FIELDS+" from message where created_date in(select max(created_date) maxdate from message where to_id=#{id} or from_id=#{id} group by conversation_id) limit #{offset},#{num}"})
    List<Message> getAllConversation(@Param("id")int id,
                                     @Param("offset")int offset,
                                     @Param("num")int num);

    @Select({"select count(id) as id from message where conversation_id=#{conversationId} and to_id=#{id} and has_read=0" })
    int getUnreadCount(@Param("conversationId")String conversationId,
                       @Param("id")int id);

    @Select({"select count(id) as id from message where conversation_id=#{conversationId}" })
    int getMessageCount(@Param("conversationId")String conversationId);

    @Update({"update message set has_read=1 where conversation_id=#{conversationId} and to_id=#{id}"})
    void updateHasRead(@Param("conversationId")String conversationId,
                       @Param("id")int id);
}