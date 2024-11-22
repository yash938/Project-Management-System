package com.Project_Management.Service;

import com.Project_Management.Dtos.CommentDto;

import java.util.List;

public interface CommentService {
    CommentDto createComment(Long issueId,Long userId,String comment);
    void deleteComment(Long commentId,Long userId) throws Exception;
    List<CommentDto> findCommentByIssueId(Long issueId);
}
