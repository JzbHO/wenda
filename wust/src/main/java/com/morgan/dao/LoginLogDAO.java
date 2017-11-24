package com.morgan.dao;

import com.morgan.model.Comment;
import com.morgan.model.LoginLog;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */
@Mapper
public interface LoginLogDAO {
    //更新 或者删除成功都返回>0
    String TABLE_NAME = " login_log ";
    String INSERT_FIELDS = "login_date,user_id ";
    String SELECT_FIELDS = " id, " + INSERT_FIELDS;


    @Insert({"insert into ", TABLE_NAME,"(", INSERT_FIELDS ,") values (#{loginDate},#{userId}"})
    int addLoginLog(@Param("loginDate") Date loginDate, @Param("userId") int userId);

    //选取最近登录的时间
    @Select({"select  from",TABLE_NAME,"where user_id=#{userId} ORDER BY login_date DESC +" +
            "LIMIT #{1},#{1}"})
    LoginLog selecLastLoginLog(@Param("userId") int userId);


}
