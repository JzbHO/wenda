package com.nowcoder.dao;

import com.nowcoder.model.Comment;
import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.apache.ibatis.annotations.*;
import org.aspectj.weaver.patterns.TypePatternQuestions;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@Mapper
public interface QusetionDAO {
    String TABLE_NAME=" question ";
    String INSERT_FIELDS=" title, content, created_date, user_id,comment_count ";
    String SELECT_FIELDS=" id, "+INSERT_FIELDS;

    //#表示与User类中的属性相匹配
    @Insert({"insert into ", TABLE_NAME,"(", INSERT_FIELDS ,") values (#{title},#{content},#{createdDate},#{userId},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select",SELECT_FIELDS,"from",TABLE_NAME,"where entity_id=#{entityId} and "+
            "entity_type=#{entitype}"})
    List<Comment> selectCommentByEntity(@Param("entityID") int entityid,@Param("entityType") int entityType);
//
//    @Update({"update",TABLE_NAME,"set password=#{password} where id=#{id}"})
//    void updatePassord(User user);

    @Delete({"delete from",TABLE_NAME,"where id=#{id}" })
    void deleteById(int id);

    List<Question> selectLatestQuestions (@Param("userid") int userid,
                                          @Param("offset") int offset,
                                          @Param("limit") int limit);

    @Select({"select ", SELECT_FIELDS, " from ", TABLE_NAME, " where id=#{id}"})
    Question getById(int id);

    @Update({"update ", TABLE_NAME, " set comment_count = #{commentCount} where id=#{id}"})
    int updateCommentCount(@Param("id") int id, @Param("commentCount") int commentCount);



}
