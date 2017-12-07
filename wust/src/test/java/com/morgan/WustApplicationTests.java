package com.morgan;
import com.morgan.dao.QuestionDAO;
import com.morgan.dao.UserDAO;
import com.morgan.model.Comment;
import com.morgan.model.EntityType;
import com.morgan.model.Question;
import com.morgan.service.CommentService;
import com.morgan.service.MessageService;
import com.morgan.service.SearchService;
import com.morgan.util.JedisAdapter;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.List;
import java.util.Random;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(WustApplication.class)
@WebAppConfiguration
public class WustApplicationTests {
	@Autowired
	UserDAO userDAO;

	@Autowired
    QuestionDAO questionDAO;

	@Autowired
	CommentService commentService;

	@Autowired
	MessageService messageService;

	@Autowired
	JedisAdapter jedisAdapter;

	@Autowired
	SearchService searchService;

	@Test
	public void contextLoads() throws Exception{
		List<Question> list=searchService.searchQuestion("请教",0,9,"h1","h1");
		System.out.println(1);


	}

}

