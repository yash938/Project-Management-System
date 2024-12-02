package com.Project_Management.Controller;

import com.Project_Management.Dto.IssueDto;
import com.Project_Management.Dto.MessageResponse;
import com.Project_Management.Dto.UserDto;
import com.Project_Management.Entity.IssueEntity;
import com.Project_Management.Entity.IssueReq;
import com.Project_Management.Entity.User;
import com.Project_Management.Service.IssueService;
import com.Project_Management.Service.UserService;
import org.apache.coyote.Response;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssueController {
    @Autowired
    private IssueService issueService;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueEntity> getIssueById(@PathVariable Long issueId){
        IssueEntity issue = issueService.getIssueId(issueId);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
        public ResponseEntity<List<IssueEntity>> getByProjectId(@PathVariable Long projectId){
        List<IssueEntity> project = issueService.getIssueByProjectId(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<IssueDto> createIssue(@RequestBody IssueReq issueReq){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();

        IssueEntity issue = issueService.createIssue(issueReq, user);
        IssueDto issueDto = new IssueDto();
        issueDto.setIssueId(issue.getIssueId());
        issueDto.setDescription(issue.getDescription());
        issueDto.setAssignee(issue.getAssign());
        issueDto.setPriority(issue.getPriority());
        issueDto.setDueDate(issue.getDueDate());
        issueDto.setProject(issue.getProject());
        issueDto.setStatus(issue.getStatus());
        issueDto.setTitle(issue.getTitle());
        issueDto.setProjectId(issue.getProjectId());
        issueDto.setProject(issue.getProject());

        return ResponseEntity.ok(issueDto);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) authentication.getPrincipal();
        issueService.deleteIssue(issueId, user.getUserId());
        MessageResponse issues = MessageResponse.builder().message("Issue deleted successfully").date(LocalDate.now()).status(HttpStatus.OK).build();
        return new ResponseEntity<>(issues,HttpStatus.OK);
    }

    @PutMapping("/{issueId}/assignee/{userId}")
    public ResponseEntity<IssueEntity> addUserIssue(@PathVariable Long issueId,@PathVariable Long userId){
        IssueEntity issue = issueService.addUserToIssue(issueId, userId);
        return new ResponseEntity<>(issue,HttpStatus.OK);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<IssueEntity> updateStatus(@PathVariable String status,@PathVariable Long issueId){
        IssueEntity issue = issueService.updateStatus(issueId, status);
        return new ResponseEntity<>(issue,HttpStatus.OK);
    }
}
