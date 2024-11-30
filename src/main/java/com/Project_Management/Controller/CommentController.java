package com.Project_Management.Controller;

import com.Project_Management.Dto.CommentDto;
import com.Project_Management.Dto.MessageResponse;
import com.Project_Management.Entity.Comment;
import com.Project_Management.Entity.User;
import com.Project_Management.Service.CommentService;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Repository
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Comment> createComment(
            @RequestBody CommentDto commentDto,
            @RequestHeader("Authorization") String jwt
            ){
        User user = userService.findUserProfileByJwt(jwt);
        Comment comment = commentService.createComment(commentDto.getIssueId(), user.getUserId(), commentDto.getContent());
        return new ResponseEntity<>(comment, HttpStatus.CREATED);
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<MessageResponse> deleteComment(@RequestHeader("Authorization") String jwt, @PathVariable Long commentId) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        commentService.deleteComment(commentId, user.getUserId());
        MessageResponse deleteComment = MessageResponse.builder().message("Comment deleted successfully").status(HttpStatus.OK).build();
        return new ResponseEntity<>(deleteComment, HttpStatus.OK);
    }

    @GetMapping("/{issueId}")
    public ResponseEntity<List<Comment>> getCommentByIssueId(@PathVariable Long issueId){
        List<Comment> comment = commentService.findCommentByIssueId(issueId);
        return new ResponseEntity<>(comment, HttpStatus.OK);
    }

}
