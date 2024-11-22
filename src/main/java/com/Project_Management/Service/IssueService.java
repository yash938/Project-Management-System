package com.Project_Management.Service;


import com.Project_Management.Dtos.IssueDto;
import com.Project_Management.Entity.IssueEntity;
import com.Project_Management.Entity.User;

import java.util.List;
import java.util.Optional;

public interface IssueService {

    IssueDto getIssueId(Long issueId);
    List<IssueDto> getIssueByProjectId(Long projectId);
    IssueDto createIssue(IssueDto issue, User user);
    Optional<IssueDto> updateIssue(Long issueId,IssueDto updateIssue,Long userId);
    void deleteIssue(Long issueId,Long userId);
    IssueDto addUserToIssue(Long issueId,Long userId);

    IssueEntity updateStatus(Long issueId, String status);

}
