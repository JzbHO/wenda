package com.morgan.service;

import com.morgan.dao.QuestionDAO;
import com.morgan.model.Question;
import javafx.scene.web.HTMLEditor;
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
    QuestionDAO questionDAO;

    @Autowired
    MySensitiveService mySensitiveService;
    public List<Question> getLatestQuestions(int userId,int offset,int n){
        return questionDAO.selectLatestQuestions(userId,offset,n);
    }


    public int addQuestion(Question question) {
        question.setTitle(HtmlUtils.htmlEscape(question.getTitle()));
        question.setContent(HtmlUtils.htmlEscape(question.getContent()));

        question.setTitle(mySensitiveService.filter(question.getTitle()));
        question.setContent(mySensitiveService.filter(question.getContent()));

        return questionDAO.addQuestion(question);
    }
}
