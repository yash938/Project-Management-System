package com.Project_Management.ServiceImplementation;

import com.Project_Management.Dtos.ChatDto;
import com.Project_Management.Dtos.ProjectDto;
import com.Project_Management.Dtos.UserDto;
import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.InvitationRepo;
import com.Project_Management.Repository.ProjectRepo;
import com.Project_Management.Service.ChatService;
import com.Project_Management.Service.ProjectService;
import com.Project_Management.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private InvitationRepo invitationRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;



    @Override
    public ProjectDto createProject(ProjectDto projectDto) {
        // Map DTO to Entity
        Project project = modelMapper.map(projectDto, Project.class);

        // Fetch the User using email
        UserDto userDto = userService.findByEmail(project.getOwner().getEmail());
        if (userDto == null) {
            throw new RuntimeException("User is not found");
        }

        // Convert UserDto to User
        User owner = modelMapper.map(userDto, User.class);
        project.setOwner(owner);

        // Save the project
        Project createdProject = projectRepo.save(project);

        // Create a chat for the project
        Chat chat = new Chat();
        chat.setProject(createdProject);

        Chat createdChat = chatService.createChat(chat);
        createdProject.setChat(createdChat);

        // Save the updated project with chat reference
        Project savedProject = projectRepo.save(createdProject);

        // Map back to DTO and return
        return modelMapper.map(savedProject, ProjectDto.class);
    }


    @Override
    public List<ProjectDto> getProjectByTeam(User user, String category, String tags) {
        List<Project> projectTeam = projectRepo.findByTeamContainingOrOwner(user, user);

        if(category != null){
            projectTeam.stream().filter(project1 -> project1.getCategory().equals(category)).collect(Collectors.toList());
        }
        if(tags != null){
            projectTeam.stream().filter(project1 -> project1.getTags().equals(tags)).collect(Collectors.toList());
        }

        ProjectDto project = modelMapper.map(projectTeam, ProjectDto.class);
        return projectTeam.stream()
                .map(project1 -> modelMapper.map(project, ProjectDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<ProjectDto> getProjectById(Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("project id is not found"));
        return (List<ProjectDto>) project;
    }

    @Override
    public void deleteProject(Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project id is not found"));
        projectRepo.delete(project);
    }

    @Override
    public ProjectDto updateProject(ProjectDto projectdto, Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project id is not found"));
        project.setName(projectdto.getName());
        project.setDescription(projectdto.getDescription());
        project.setTags(projectdto.getTags());

        Project save = projectRepo.save(project);

        return modelMapper.map(save,ProjectDto.class);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("project id is not found"));
        User user = userService.findByUserId(userId);
        if(!project.getTeam().contains(user)){{
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }}

        projectRepo.save(project);
    }

    @Override
    public void removeUserToProject(Long projectId, Long userId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("project id is not found"));
        User user = userService.findByUserId(userId);
        if(!project.getTeam().contains(user)){{
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }}

        projectRepo.save(project);
    }

    @Override
    public ChatDto getChatByProjectId(Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("project id is not found"));
        Chat chat = project.getChat();

        return modelMapper.map(chat,ChatDto.class);
    }


    @Override
    public List<ProjectDto> searchProject(String keyword, UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);
        List<Project> project = projectRepo.findByNameContainingAndTeamContaining(keyword, user);
        List<ProjectDto> projectDtos = project.stream()
                .map(projects -> modelMapper.map(projects, ProjectDto.class))
                .collect(Collectors.toList());
        return projectDtos;
    }
}
