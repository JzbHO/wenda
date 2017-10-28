package com.morgan.dao;

import com.morgan.model.Comment;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */
@Mapper
public interface CommentDAO {
    //更新 或者删除成功都返回>0
    String TABLE_NAME = " comment ";
    String INSERT_FIELDS = " content,user_id, created_date, entity_id, entity_type,status  ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;

    //#表示与User类中的属性相匹配
    @Insert({"insert into ", TABLE_NAME,"(", INSERT_FIELDS ,") values (#{content},#{userId},#{createdDate},#{entityId},#{entityType}.#{status)"})
    int addComment(Comment comment);

    //选取类型所有评论
    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where entity_id=#{entityId} and "+
            "entity_type=#{entityType}"})
    //这三个参数便是接口
    List<Comment> selectCommentByEntity(@Param("entityId") int entityid, @Param("entityType") int entityType);

    //选取类型评论数
    @Select({"select count(id)  from",TABLE_NAME,"where entity_id=#{entityId} and "+
            "entity_type=#{entityType}"})
    int getCommentCount(@Param("entityId") int entityid, @Param("entityType") int entityType);

    @Update({"update comment set status=#{status} where id=#{id}"})
    int updateStatus(@Param("status") int status,@Param("entityId") int id);


}
