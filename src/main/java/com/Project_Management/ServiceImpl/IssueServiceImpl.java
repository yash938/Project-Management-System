package com.Project_Management.ServiceImpl;

import com.Project_Management.Entity.IssueEntity;
import com.Project_Management.Entity.IssueReq;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.IssueRepo;
import com.Project_Management.Repository.ProjectRepo;
import com.Project_Management.Service.IssueService;
import com.Project_Management.Service.ProjectService;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IssueServiceImpl implements IssueService {

    @Autowired
    private IssueRepo issueRepo;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserService userService;

    @Override
    public IssueEntity getIssueId(Long issueId) {
        IssueEntity issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("IssueId not found"));
        return issue;
    }

    @Override
    public List<IssueEntity> getIssueByProjectId(Long projectId) {
        List<IssueEntity> byProjectId = issueRepo.findByProjectId(projectId);
        return byProjectId;
    }

    @Override
    public IssueEntity createIssue(IssueReq issueReq, User user) {
        List<Project> project = projectService.getProjectById(issueReq.getProjectId());
        IssueEntity issue = new IssueEntity();
        issue.setTitle(issueReq.getTitle());
        issue.setDescription(issueReq.getDescription());
        issue.setPriority(issueReq.getPriority());
        issue.setStatus(issueReq.getStatus());
        issue.setDueDate(issueReq.getDueDate());


        issue.setProject((Project) project);
        IssueEntity createIssue = issueRepo.save(issue);

        return createIssue;
    }

    @Override
    public Optional<IssueEntity> updateIssue(Long issueId, IssueEntity updateIssue, Long userId) {

        return Optional.empty();
    }

    @Override
    public void deleteIssue(Long issueId, Long userId) {
        IssueEntity issueId1 = getIssueId(issueId);
        issueRepo.delete(issueId1);
    }

    @Override
    public IssueEntity addUserToIssue(Long issueId, Long userId) {
        User user = userService.getById(userId);
        IssueEntity issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Could not find"));
        issue.setAssign(user);
        IssueEntity save = issueRepo.save(issue);
        return save;
    }

    @Override
    public IssueEntity updateStatus(Long issueId, String status) {
        IssueEntity issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Could not find"));
        issue.setStatus(status);
        return issueRepo.save(issue);
    }
}
