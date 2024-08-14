package com.example.Farming_App.services.impl;

import com.example.Farming_App.entity.Account;
import com.example.Farming_App.entity.Comment;
import com.example.Farming_App.entity.Post;
import com.example.Farming_App.exception.ResourceNotFoundException;
import com.example.Farming_App.repositories.AccountRepository;
import com.example.Farming_App.repositories.CommentRepository;
import com.example.Farming_App.services.CommentService;
import com.example.Farming_App.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final PostService postService;
    private final AccountRepository accountRepository;

    public boolean addComment(Long idPost, String content){
        Post post=postService.getPost(idPost);

        Comment comment=new Comment();
        comment.setPost(post);
        comment.setContent(content);
        comment.setParentId(getAccount().orElseThrow(
                ()->new ResourceNotFoundException("Account","username","...")
        ));
        return commentRepository.save(comment)!=null;

    }

    public boolean deleteComment(Long commentId){
        commentRepository.findById(commentId)
                .orElseThrow(
                            ()-> new ResourceNotFoundException("Comment","id",String.valueOf(commentId))
                );

        commentRepository.deleteById(commentId);
        return true;

    }
    private Optional<Account> getAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username ="";
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            username = ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return accountRepository.findByMail(username);
    }
}
