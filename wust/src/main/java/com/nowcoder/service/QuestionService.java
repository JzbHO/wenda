package com.nowcoder.service;

import com.nowcoder.dao.QuestionDAO;
import com.nowcoder.dao.QuestionDAO;
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
    QuestionDAO questionDAO;

    public List<Question> getLatestQuestions(int userId,int offset,int n){
        return questionDAO.selectLatestQuestions(userId,offset,n);
    }




}
