package com.Project_Management.ServiceImplementation;

import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.ProjectRepo;
import com.Project_Management.Service.ChatService;
import com.Project_Management.Service.ProjectService;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;

    @Autowired
    private UserService userService;

    @Autowired
    private ChatService chatService;

    @Override
    public Project createProject(Project project, User user) {
        User email = userService.findByEmail(user.getEmail());
        if(email == null){
            throw new UsernameNotFoundException("user email is not found");
        }

        project.setOwner(email);
        project.setName(project.getName());
        project.setCategory(project.getCategory());
        project.setTags(project.getTags());
        project.getTeam().add(email);

        Project createProject = projectRepo.save(project);

        Chat chat = new Chat();
        chat.setProject(createProject);
        Chat projectChat = chatService.createChat(chat);
        createProject.setChat(projectChat);
        return createProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tags) {
        List<Project> ownerProject = projectRepo.findByTeamContainingOrOwner(user, user);

        if(category != null){
            ownerProject.stream().filter(project -> project.getCategory().equals(category)).collect(Collectors.toList());
        }

        if(tags != null){
            ownerProject.stream().filter(project -> project.getTags().contains(tags)).collect(Collectors.toList());

        }
        return ownerProject;
    }

    @Override
    public Project getProjectById(Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new UsernameNotFoundException("User id is not found"));
        return project;
    }

    @Override
    public void deleteProject(Long projectId, Long userId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project id is not found"));
        projectRepo.delete(project);
    }

    @Override
    public Project updateProject(Project project, Long projectId) {
        Project project1 = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project id is not found"));
        project.setDescription(project.getDescription());
        project1.setName(project.getName());
        project1.setCategory(project.getCategory());
        project1.setTags(project.getTags());
        return projectRepo.save(project1);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) {

        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("project id is not found"));
        User user = userService.findByUserId(userId);
        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }

        projectRepo.save(project);
    }

    @Override
    public void removeUserToProject(Long projectId, Long userId) {

        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("project id is not found"));
        User user = userService.findByUserId(userId);
        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);
        }

        projectRepo.save(project);
    }

    @Override
    public Chat getChatByProjectId(Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("project id is not found"));
        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) {
        List<Project> project = projectRepo.findByNameContainingAndTeamContaining(keyword, user);
        return project;
    }
}
