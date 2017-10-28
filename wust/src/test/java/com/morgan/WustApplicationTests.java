package com.morgan;
import com.morgan.dao.QuestionDAO;
import com.morgan.dao.UserDAO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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

