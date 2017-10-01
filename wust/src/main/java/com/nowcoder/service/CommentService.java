package com.nowcoder.service;

import com.nowcoder.dao.CommentDAO;
import com.nowcoder.model.Comment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/8/12 0012.
 */
@Service
public class CommentService {
    @Autowired
    CommentDAO commentDAO;

    public List<Comment> getComment(int entityId, int entityType){
        return commentDAO.selectCommentByEntity(entityId,entityType);
    }
    public int addComment(Comment comment){
        return commentDAO.addComment(comment)>0?comment.getId():0;
    }
    public int getCommentCount(int entityId,int entityType){
        return commentDAO.getCommentCount(entityId,entityType);
    }
    public boolean deleteComment(int commentId){
        return commentDAO.updateStatus(1,commentId)>0;
    }

    public Comment getCommenById(){
        return new Comment();
    }





}
