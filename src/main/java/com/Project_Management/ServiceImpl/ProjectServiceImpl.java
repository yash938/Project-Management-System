package com.Project_Management.ServiceImpl;

import com.Project_Management.Dto.UserDto;
import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.ChatRepo;
import com.Project_Management.Repository.ProjectRepo;
import com.Project_Management.Repository.UserRepo;
import com.Project_Management.Service.ChatService;
import com.Project_Management.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepo projectRepo;
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ChatService chatService;
    @Autowired
    private ChatRepo chatRepo;

    @Override
    public Project createProject(Project project,User user)  {
        Project project1 = new Project();
        project1.setName(project.getName());
        project1.setOwner(user);
        project1.setCategory(project.getCategory());
        project1.setDescription(project.getDescription());
        project1.setTags(project.getTags());
        project1.getTeam().add(user);

        Project createProject = projectRepo.save(project1);

        Chat chat = new Chat();
        chat.setProject(createProject);
        Chat chat1 = chatService.createChat(chat);
        createProject.setChat(chat1);

        return createProject;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tags) {
        List<Project> projects = projectRepo.findByTeamContainingOrOwner(user, user);

        if(category != null){
            projects = projects.stream().filter(project -> project.getCategory().equals(category)).collect(Collectors.toList());
        }

        if(tags != null){
            projects = projects.stream().filter(project -> project.getTags().contains(tags)).collect(Collectors.toList());
        }

        return projects;
    }

    @Override
    public Project getProjectById(Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project id is not found"));
        return  project;
    }

    @Override
    public void deleteProject(Long projectId) {
        projectRepo.deleteById(projectId);
    }

    @Override
    public Project updateProject(Project project, Long projectId) {
        Project updateProject = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project id is not found"));
        updateProject.setName(project.getName());
        updateProject.setTags(project.getTags());
        updateProject.setDescription(project.getDescription());
        return projectRepo.save(updateProject);
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user id is not found"));
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project id is not found"));

        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);
        }

        projectRepo.save(project);
    }

    @Override
    public void removeUserToProject(Long projectId, Long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new RuntimeException("user id is not found"));
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new RuntimeException("Project id is not found"));

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
