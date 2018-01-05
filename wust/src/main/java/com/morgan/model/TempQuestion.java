package com.morgan.model;

import com.morgan.service.QuestionService;

/**
 * Created by Administrator on 2018/1/5 0005.
 */
public class TempQuestion {
    private User user;
    private Question question;
    private Long followCount;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public Long getFollowCount() {
        return followCount;
    }

    public void setFollowCount(Long followCount) {
        this.followCount = followCount;
    }
}
