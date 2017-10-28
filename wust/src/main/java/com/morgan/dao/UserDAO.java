package com.morgan.dao;

import com.morgan.model.User;
import org.apache.ibatis.annotations.*;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@Mapper
public interface UserDAO {
   String TABLE_NAME=" user ";
   String INSERT_FILEDS=" name,password,salt,head_url ";

   @Insert({"insert into"+TABLE_NAME+"("+INSERT_FILEDS+")"+"values"+
                    "(#{name},#{password},#{salt},#{headUrl})"})
    int addUser(User user);

    @Select({"select "+"id,"+INSERT_FILEDS+ " from "+TABLE_NAME+"where id=#{id}"})
    User selectUser(int id);

    @Update({"update "+TABLE_NAME+"set password=#{password} where id=#{id}"})
    int updateUser(User user);

    @Delete({"delete from "+TABLE_NAME+"where id=#{id}"})
    int deleteUser(int id);

    @Select({"select "+"id,"+INSERT_FILEDS+ " from "+TABLE_NAME+"where name=#{name}"})
    User selectByName(String name);
}
