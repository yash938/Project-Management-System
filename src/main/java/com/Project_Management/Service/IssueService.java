package com.Project_Management.Service;



import com.Project_Management.Entity.IssueEntity;
import com.Project_Management.Entity.IssueReq;
import com.Project_Management.Entity.User;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    IssueEntity getIssueId(Long issueId);

    List<IssueEntity> getIssueByProjectId(Long projectId);
    IssueEntity createIssue(IssueReq issueReq, User user);
    Optional<IssueEntity> updateIssue(Long issueId,IssueEntity updateIssue,Long userId);
    void deleteIssue(Long issueId,Long userId);
    IssueEntity addUserToIssue(Long issueId,Long userId);

    IssueEntity updateStatus(Long issueId, String status);

}
