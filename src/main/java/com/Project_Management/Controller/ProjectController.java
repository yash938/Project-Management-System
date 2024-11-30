package com.Project_Management.Controller;

import com.Project_Management.Dto.MessageResponse;
import com.Project_Management.Entity.*;
import com.Project_Management.Service.InvitationService;
import com.Project_Management.Service.ProjectService;
import com.Project_Management.Service.UserService;
import jakarta.mail.MessagingException;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private InvitationService invitationService;

    @Autowired
    private UserService userService;
    @GetMapping("/{projectId}")
    public ResponseEntity<List<Project>> getProjectById(@PathVariable Long projectId){
        List<Project> projectById = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projectById,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Project>> getProjects(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tag,
            @RequestHeader("Authorization") String jwt
    ){
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projectByTeam = projectService.getProjectByTeam(user, category, tag);
        return new ResponseEntity<>(projectByTeam,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project,@RequestHeader("Authorization") String jwt){
        User user = userService.findUserProfileByJwt(jwt);
        Project createProjects = projectService.createProject(project, user);
        return new ResponseEntity<>(createProjects,HttpStatus.CREATED);
    }
    @PutMapping("/{projectId}")
    public ResponseEntity<Project> updateProject(@RequestBody Project project,@RequestHeader("Authorization") String jwt ,Long projectId ){
        User user = userService.findUserProfileByJwt(jwt);
        Project updatedProjects = projectService.updateProject(project, projectId);
        return new ResponseEntity<>(updatedProjects,HttpStatus.CREATED);
    }
    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable Long projectId, @RequestHeader("Authorization") String jwt ){
        User user = userService.findUserProfileByJwt(jwt);
        projectService.deleteProject(projectId);
        MessageResponse deletedProject = MessageResponse.builder().message("Successfully deleted project").status(HttpStatus.OK).date(LocalDate.now()).build();
        return new ResponseEntity<>(deletedProject,HttpStatus.CREATED);
    }

    @GetMapping("/search")
    public ResponseEntity<List<Project>> searchProject(@RequestParam(required = false) String keyword, @RequestHeader("Authorization") String jwt ){
        User user = userService.findUserProfileByJwt(jwt);
        List<Project> projects = projectService.searchProject(keyword, user);
        return new ResponseEntity<>(projects,HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Chat> getChatByProject(@PathVariable Long projectId){
        Chat chatByProjectId = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chatByProjectId,HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(@RequestBody InvitationReq request, @RequestHeader("Authorization") String jwt,@RequestBody Project project) throws MessagingException {
        User user = userService.findUserProfileByJwt(jwt);
        invitationService.sendInvitation(request.getEmail(), request.getProjectId());
        MessageResponse inviationSend = MessageResponse.builder().message("User Invitation send successfully").date(LocalDate.now()).status(HttpStatus.OK).build();
        return new ResponseEntity<>(inviationSend,HttpStatus.CREATED);
    }

    @PostMapping("/acceptInvite")
    public ResponseEntity<Invitation> acceptProject(@RequestParam(required = false) String token, @RequestHeader("Authorization") String jwt,@RequestBody Project project) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Invitation invitation = invitationService.acceptInvitation(token, user.getUserId());
        projectService.addUserToProject(user.getUserId(), invitation.getProjectId());
        MessageResponse inviationSend = MessageResponse.builder().message("User Invitation send successfully").date(LocalDate.now()).status(HttpStatus.OK).build();
        return new ResponseEntity<>(invitation,HttpStatus.OK);
    }





}
