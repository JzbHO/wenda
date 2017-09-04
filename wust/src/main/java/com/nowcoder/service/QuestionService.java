package com.nowcoder.service;

import com.nowcoder.dao.QusetionDAO;
import com.nowcoder.model.Question;
import org.aspectj.weaver.patterns.TypePatternQuestions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.util.HtmlUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19 0019.
 */
@Service
public class QuestionService {
    @Autowired
    QusetionDAO qusetionDAO;

    public List<Question> getLatestQuestions(int userId,int offset,int n){
        return qusetionDAO.selectLatestQuestions(userId,offset,n);
    }

    public int addQuestion (Question question){
        //html过滤
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));

        return qusetionDAO.addQuestion(question)>0 ?question.getId():0;
    }

    public Question getById(int id){
        return qusetionDAO.getById(id);
    }

    public int updateCommentCount(int id,int count){
        return qusetionDAO.updateCommentCount(id,count);

    }



}
