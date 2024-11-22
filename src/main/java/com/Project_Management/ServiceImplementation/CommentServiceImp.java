package com.Project_Management.ServiceImplementation;

import com.Project_Management.Dtos.CommentDto;
import com.Project_Management.Dtos.IssueDto;
import com.Project_Management.Entity.Comment;
import com.Project_Management.Entity.IssueEntity;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.CommentRepo;
import com.Project_Management.Repository.IssueRepo;
import com.Project_Management.Repository.UserRepo;
import com.Project_Management.Service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class CommentServiceImp implements CommentService {

    @Autowired
    private CommentRepo commentRepo;

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public CommentDto createComment(Long issueId, Long userId, String comment) {
        // Issue fetch karna
        IssueEntity issue = issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found with ID: " + issueId));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        Comment newComment = new Comment();
        newComment.setContent(comment);
        newComment.setCreatedDateTime(LocalDate.now());
        newComment.setIssue(issue);
        newComment.setUser(user);
        Comment savedComment = commentRepo.save(newComment);
        CommentDto create = modelMapper.map(savedComment, CommentDto.class);

        return create;
    }

    @Override
    public void deleteComment(Long commentId, Long userId) throws Exception {

        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user Id is not found"));
        Comment comment = commentRepo.findById(commentId).orElseThrow(() -> new RuntimeException("Comment id is not found"));

        if(comment.getUser().equals(user)){
            commentRepo.delete(comment);
        }else {
            throw new Exception("User have does not to delete this comment");
        }

    }

    @Override
    public List<CommentDto> findCommentByIssueId(Long issueId) {
        IssueEntity issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Issue id is not found"));
        IssueDto issues = modelMapper.map(issue, IssueDto.class);
        return (List<CommentDto>) issues;
    }
}
