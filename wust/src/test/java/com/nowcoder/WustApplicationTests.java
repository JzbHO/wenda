package com.nowcoder;
import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.TestDao;
import com.nowcoder.dao.UserDAO;
import com.nowcoder.model.Question;
import com.nowcoder.model.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Date;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WustApplication.class)
@WebAppConfiguration
public class WustApplicationTests {
	@Autowired
	UserDAO userDAO;

	@Autowired
    QuestionDAO questionDAO;

	@Test
	public void contextLoads() {
	    System.out.print("1");
		Random random=new Random();
//		for(int i=1;i<20;i++){
//		    Question question=new Question();
//		    question.setTitle(String.format("Q%d",i));
//		    question.setContent("小明");
//		    question.setUserId(1);
//		    question.setCommentCount(1);
//		    question.setCreatedDate(new Date());
//		    questionDAO.addQuestion(question);
//		}
		questionDAO.selectLatestQuestions(1,0,2);





	}

}

