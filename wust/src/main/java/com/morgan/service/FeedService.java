package com.morgan.service;

import com.morgan.dao.FeedDAO;
import com.morgan.dao.UserDAO;
import com.morgan.model.Feed;
import com.morgan.model.User;
import com.morgan.model.ViewObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/11/4 0004.
 */
@Service
public class FeedService {
    @Autowired
    FeedDAO feedDAO;

    @Autowired
    UserDAO userDAO;
    public int addFeed(Feed feed){
        return feedDAO.addFeed(feed);
    }

    public List<Feed> getUserFeeds(int maxId,List<Integer> userIds,int count){
        return feedDAO.selectUserFeeds(maxId,userIds,count);
    }

    public Feed geFeedById(int feedId) {
        return feedDAO.getFeedById(feedId);
    }



}
