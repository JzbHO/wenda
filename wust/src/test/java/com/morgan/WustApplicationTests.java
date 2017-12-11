package com.morgan;
import com.alibaba.fastjson.JSONObject;
import com.morgan.async.EventModel;
import com.morgan.async.EventType;
import com.morgan.dao.FeedDAO;
import com.morgan.dao.MessageDAO;
import com.morgan.dao.QuestionDAO;
import com.morgan.dao.UserDAO;
import com.morgan.model.*;
import com.morgan.service.CommentService;
import com.morgan.service.FeedService;
import com.morgan.service.MessageService;
import com.morgan.service.SearchService;
import com.morgan.util.JedisAdapter;
import com.morgan.util.TimeLineType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.*;


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

	@Autowired
	MessageDAO messageDAO;

	@Autowired
	FeedService feedService;

	@Autowired
	FeedDAO feedDAO;


	@Test

	public void contextLoads() throws Exception{
		Feed feed=new Feed();
		feed.setUserId(1);
		feed.setCreateDate(new Date());
		feed.setType(TimeLineType.FOCUS);
		feed.setData("问题");
		feedDAO.addFeed(feed);

	}
	class A{
		private int x;

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}
	}

}

