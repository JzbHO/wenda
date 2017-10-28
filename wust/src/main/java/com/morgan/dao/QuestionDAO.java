package com.morgan.dao;

import com.morgan.model.Question;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@Mapper
public interface QuestionDAO {
    String TABLE_NAME=" question ";
    String INSERT_FILEDS=" title,content,user_id,created_date,comment_count ";
    String SELECT_FILEDS="id, "+INSERT_FILEDS;

    @Insert({"insert into"+TABLE_NAME+"("+INSERT_FILEDS+")"+"values"+
            "(#{title},#{content},#{userId},#{createdDate},#{commentCount})"})
    int addQuestion(Question question);

    @Select({"select "+SELECT_FILEDS+ " from "+TABLE_NAME+"where id=#{id}"})
    Question selectQuestion(int id);

    List<Question> selectLatestQuestions(@Param("userId") int userId,
                                         @Param("offset")int offset,
                                         @Param("limit")int limit);

    @Delete({"delete from "+TABLE_NAME+"where id=#{id}"})
    int deleteQuestion(int id);


}
