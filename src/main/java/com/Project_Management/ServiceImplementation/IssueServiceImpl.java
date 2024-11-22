package com.Project_Management.ServiceImplementation;

import com.Project_Management.Dtos.IssueDto;
import com.Project_Management.Dtos.ProjectDto;
import com.Project_Management.Entity.IssueEntity;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.IssueRepo;
import com.Project_Management.Service.IssueService;
import com.Project_Management.Service.ProjectService;
import com.Project_Management.Service.UserService;
import org.modelmapper.ModelMapper;
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
    private ModelMapper modelMapper;

    @Autowired
    private UserService userService;
    @Override
    public IssueDto getIssueId(Long issueId) {
        IssueEntity issueEntity = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("IssueId is not found...."));
        return modelMapper.map(issueEntity,IssueDto.class);
    }

    @Override
    public List<IssueDto> getIssueByProjectId(Long projectId) {
        List<IssueDto> byProjectId = issueRepo.findByProjectId(projectId);
        return byProjectId;
    }

    @Override
    public IssueDto createIssue(IssueDto issue, User user) {
        List<ProjectDto> projectById = projectService.getProjectById(issue.getProjectId());
        Project project = modelMapper.map(projectById, Project.class);
        IssueEntity issues  = new IssueEntity();
        issues.setStatus(issue.getStatus());
        issues.setDueDate(issue.getDueDate());
        issues.setDescription(issues.getDescription());
        issues.setTitle(issues.getTitle());
        issues.setPriority(issue.getPriority());
        issues.setProjectId(issue.getProjectId());

        issues.setProject(project);

        IssueEntity save = issueRepo.save(issues);

        return modelMapper.map(save,IssueDto.class);
    }

    @Override
    public Optional<IssueDto> updateIssue(Long issueId, IssueDto updateIssue, Long userId) {
        return Optional.empty();
    }


    @Override
    public void deleteIssue(Long issueId, Long userId) {
         getIssueId(issueId);
        issueRepo.deleteById(issueId);
    }

    @Override
    public IssueDto addUserToIssue(Long issueId, Long userId) {
        // Fetch the User
        User user = userService.findByUserId(userId);
        if (user == null) {
            throw new RuntimeException("User not found with ID: " + userId);
        }
        IssueEntity issueEntity = issueRepo.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found with ID: " + issueId));
        issueEntity.setUser(user);

        IssueEntity updatedIssue = issueRepo.save(issueEntity);

        return modelMapper.map(updatedIssue, IssueDto.class);
    }


    @Override
    public IssueEntity updateStatus(Long issueId, String status) {
        IssueEntity issue = issueRepo.findById(issueId).orElseThrow(() -> new RuntimeException("Isssue id is not found"));
        issue.setStatus(status);
        IssueEntity save = issueRepo.save(issue);
        return save;
    }

}
