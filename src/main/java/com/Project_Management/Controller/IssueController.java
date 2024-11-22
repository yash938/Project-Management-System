package com.Project_Management.Controller;

import com.Project_Management.Dtos.IssueDto;
import com.Project_Management.Dtos.MessageResponse;
import com.Project_Management.Entity.IssueEntity;
import com.Project_Management.Entity.User;
import com.Project_Management.Service.IssueService;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssueController {

    @Autowired
    private IssueService issueService;

    @Autowired
    private UserService userService;

    @GetMapping("/{issueId}")
    public ResponseEntity<IssueDto> getIssueById(@PathVariable Long issueId){
        IssueDto issueId1 = issueService.getIssueId(issueId);
        return new ResponseEntity<>(issueId1, HttpStatus.OK);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<IssueDto>> getIssueByProjectId(@PathVariable  Long projectId){
        List<IssueDto> issueByProjectId = issueService.getIssueByProjectId(projectId);
        return new ResponseEntity<>(issueByProjectId,HttpStatus.OK);
    }

    @PostMapping("/createIssue")
    public ResponseEntity<IssueDto> createIssue(
            @RequestBody IssueDto issueDto,
            @AuthenticationPrincipal User user) {

        System.out.println("Received issue: " + issueDto);
        User byUserId = userService.findByUserId(user.getUserId());
        IssueDto createdIssue = issueService.createIssue(issueDto, byUserId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdIssue);
    }

    @DeleteMapping("/{issueId}")
    public ResponseEntity<MessageResponse> deleteIssue(@PathVariable Long issueId,@RequestBody User user){
        User userId = userService.findByUserId(user.getUserId());
        issueService.deleteIssue(issueId, userId.getUserId());

        MessageResponse build = MessageResponse.builder().message("Issue deleted successfully...").httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(build,HttpStatus.OK);
    }


    @PutMapping("/{issueId}/assign/{userId}")
    public ResponseEntity<IssueDto> addUserToIssue(@PathVariable Long issueId,@PathVariable Long userId){
        IssueDto issueDto = issueService.addUserToIssue(issueId, userId);
        return new ResponseEntity<>(issueDto,HttpStatus.OK);
    }

    @PutMapping("/{issueId}/status/{status}")
    public ResponseEntity<IssueEntity> updateStatus(@PathVariable Long issueId,@PathVariable String status){
        IssueEntity issueEntity = issueService.updateStatus(issueId, status);
        return new ResponseEntity<>(issueEntity,HttpStatus.OK);
    }

}
