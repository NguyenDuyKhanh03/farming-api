package com.example.Farming_App.services;

public interface CommentService {
    boolean addComment(Long idPost, String content);
    boolean deleteComment(Long commentId);

}
