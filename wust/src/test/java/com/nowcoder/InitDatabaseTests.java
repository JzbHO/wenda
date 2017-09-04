package com.nowcoder;

import com.nowcoder.dao.QusetionDAO;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.Random;

/**
 * Created by Administrator on 2017/7/18 0018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class InitDatabaseTests {
    @Autowired
    UserDAO userDAO;

    @Autowired
    QusetionDAO qusetionDAO;

    @Test
    public void initbase(){
        Random rando=new Random();
        User user=new User("Jiang");
        user.setPassword("123");
        userDAO.updatePassord(user);
        userDAO.deleteById(2);

        Question question=new Question();
        question.setCommentCount(1);
        Date date=new Date();
        date.setTime(date.getTime());
        question.setCreatedDate(date);

        qusetionDAO.addQuestion(question);



//        for(int i=0;i<11;++i){
//
//        }

    }
}
