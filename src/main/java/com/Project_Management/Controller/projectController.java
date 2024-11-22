package com.Project_Management.Controller;

import com.Project_Management.Dtos.*;
import com.Project_Management.Entity.User;
import com.Project_Management.Service.InvitationService;
import com.Project_Management.Service.ProjectService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/project")
public class projectController{

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private InvitationService invitationService;

    @PostMapping("/create")
    public ResponseEntity<ProjectDto> createProject(ProjectDto projectDto){
        ProjectDto project = projectService.createProject(projectDto);
        return new ResponseEntity<>(project, HttpStatus.CREATED);
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<List<ProjectDto>> getProjectById(@PathVariable Long projectId){
        List<ProjectDto> projectById = projectService.getProjectById(projectId);
        return new ResponseEntity<>(projectById,HttpStatus.OK);
    }


    @GetMapping
    public ResponseEntity<List<ProjectDto>> getAllProject(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String tags,
            @RequestBody UserDto userDto

            ){
        User user = modelMapper.map(userDto, User.class);
        List<ProjectDto> projectByTeam = projectService.getProjectByTeam(user, category, tags);
        return new ResponseEntity<>(projectByTeam,HttpStatus.OK);
    }

    @PutMapping("/{projectId}")
    public ResponseEntity<ProjectDto> updateProject(@PathVariable long projectId,@RequestBody ProjectDto projectDto){
        ProjectDto updateProject = projectService.updateProject(projectDto, projectId);
        return new ResponseEntity<>(updateProject,HttpStatus.OK);
    }


    @DeleteMapping("/{projectId}")
    public ResponseEntity<MessageResponse> deleteProject(@PathVariable long projectId){
        projectService.deleteProject(projectId);
        MessageResponse deleted =MessageResponse.builder().message("Project deleted successfully ....!!").httpStatus(HttpStatus.OK).build();
        return new ResponseEntity<>(deleted,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProjectDto>> searchProjects(
            @RequestParam String keyword,
            @RequestBody UserDto userDto
    ){
        List<ProjectDto> projectDtos = projectService.searchProject(keyword, userDto);
        return new ResponseEntity<>(projectDtos,HttpStatus.OK);
    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<ChatDto> getChatByProjectId(@PathVariable long projectId) {
        ChatDto chatByProjectId = projectService.getChatByProjectId(projectId);
        return new ResponseEntity<>(chatByProjectId,HttpStatus.OK);
    }

    @PostMapping("/invite")
    public ResponseEntity<MessageResponse> inviteProject(@RequestBody InviteRequest inviteRequest) throws MessagingException {
        invitationService.sendInvitation(inviteRequest.getEmail(),inviteRequest.getProjectId());
        MessageResponse userInvitationSend = MessageResponse.builder().httpStatus(HttpStatus.OK).message("User invitation send").build();
        return new ResponseEntity<>(userInvitationSend,HttpStatus.OK);
    }

    @GetMapping("/acceptInvite")
    public ResponseEntity<InvitationDto> acceptInvitationProject(@RequestParam String token,@RequestParam Long userId) throws Exception {
        InvitationDto invitationDto = invitationService.acceptInvitation(token, userId);
        projectService.addUserToProject(invitationDto.getProjectId(),userId);
        return new ResponseEntity<>(invitationDto,HttpStatus.ACCEPTED);
    }





}