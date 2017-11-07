package com.morgan.dao;

import com.morgan.model.Comment;
import com.morgan.model.Feed;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */
@Mapper
public interface FeedDAO {
    String TABLE_NAME = " feed ";
    String INSERT_FIELDS = "created_date,user_id,date,type";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;



    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{entityId} and "+
            "entity_type=#{entityType}"})

    List<Feed> selectUserFeeds(@Param("maxId") int maxId,
                               @Param("userIds") List<Integer> userIds,
                               @Param("count") int count);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where id=#{feedId}"})
    Feed getFeedById(int feedId);

    @Insert({"insert into ", TABLE_NAME,"(", INSERT_FIELDS ,") values (#{created_date},#{user_id},#{date},#{type})"})
    int addFeed(Feed feed);



}
