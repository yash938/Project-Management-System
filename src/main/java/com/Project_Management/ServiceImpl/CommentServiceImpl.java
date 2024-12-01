package com.Project_Management.ServiceImpl;

import com.Project_Management.Entity.Comment;
import com.Project_Management.Entity.IssueEntity;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.CommentRepo;
import com.Project_Management.Repository.IssueRepo;
import com.Project_Management.Repository.UserRepo;
import com.Project_Management.Service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private IssueRepo issueRepo;

    @Override
    public Comment createComment(Long issueId, Long userId, String content) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("User id is not found"));
        IssueEntity issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("issue id is not found"));

        Comment comment1 = new Comment();
        comment1.setIssue(issue);
        comment1.setUser(user);
        comment1.setCreatedDateTime(LocalDate.now());
        comment1.setContent(content);
        Comment createComment = commentRepo.save(comment1);
        issue.getComments().add(createComment);
        return createComment;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("issue id is not found"));
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user id is not found"));
        if(comment.getUser().equals(user)){
            commentRepo.delete(comment);
        }else{
            throw new Exception("User does not have to delete the commeent");
        }
    }

    @Override 
    public List<Comment> findCommentByIssueId(Long issueId) {
        return commentRepo.findByIssue_IssueId(issueId);
    }
}
