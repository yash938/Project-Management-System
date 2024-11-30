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
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Repository
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
        public ResponseEntity<List<IssueEntity>> getIssueByProjectId(@PathVariable Long projectId){
        List<IssueEntity> project = issueService.getIssueByProjectId(projectId);
        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<IssueDto> createIssue(@RequestBody IssueReq issueReq, @RequestHeader("Authorization") String token){

        User tokenUser = userService.findUserProfileByJwt(token);
        User user = userService.getById(tokenUser.getUserId());

        IssueEntity issue = issueService.createIssue(issueReq, user);
        IssueDto issueDto = new IssueDto();
        issueDto.setDescription(issue.getDescription());
        issueDto.setAssignee(issue.getAssign());
        issueDto.setPriority(issue.getPriority());
        issueDto.setDueDate(issue.getDueDate());
        issueDto.setProject(issue.getProject());
        issueDto.setStatus(issueDto.getStatus());
        issueDto.setTitle(issueDto.getTitle());
        issueDto.setProjectId(issueDto.getProjectId());
        issueDto.setProject(issueDto.getProject());

        return ResponseEntity.ok(issueDto);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,@RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfileByJwt(jwt);
        issueService.deleteIssue(issueId, user.getUserId());
        MessageResponse issues = MessageResponse.builder().message("Issue deleted successfully").date(LocalDate.now()).status(HttpStatus.OK).build();
        return new ResponseEntity<>(issues,HttpStatus.OK);
    }

    @PutMapping("/{issueID}/assignee/{userId}")
    public ResponseEntity<IssueEntity> addUserIssue(@PathVariable Long issueId,@PathVariable Long userId){
        IssueEntity issue = issueService.addUserToIssue(issueId, userId);
        return new ResponseEntity<>(issue,HttpStatus.OK);
    }

    @PutMapping("/{issueID}/status/{status}")
    public ResponseEntity<IssueEntity> updateStatus(@PathVariable String status,@PathVariable Long issueId){
        IssueEntity issue = issueService.updateStatus(issueId, status);
        return new ResponseEntity<>(issue,HttpStatus.OK);
    }
}
