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
           Project createProject = new Project();
           createProject.setOwner(user);
           createProject.setName(project.getName());
           createProject.setCategory(project.getCategory());
           createProject.setTags(project.getTags());
           createProject.setDescription(project.getDescription());
           createProject.getTeam().add(user);

        Project saveProjects = projectRepo.save(createProject);

        Chat chat = new Chat();
        chat.setProject(saveProjects);

        Chat chat1 = chatService.createChat(chat);
        saveProjects.setChat(chat1);
        //2:42
        return saveProjects;
    }

    @Override
    public List<Project> getProjectByTeam(User user, String category, String tags) {
        List<Project> project = projectRepo.findByTeamContainingOrOwner(user, user);

        if(category!=null){
            project = project.stream().filter(project1 -> project1.getCategory().equals(category)).collect(Collectors.toList());
        }
        if(tags!=null){
            project = project.stream().filter(project1 -> project1.getTags().contains(tags)).collect(Collectors.toList());
        }


        return project;
    }

    @Override
    public Project getProjectById(Long projectId) {
        Project userIdIsnotFound = projectRepo.findById(projectId).orElseThrow(() -> new UsernameNotFoundException("User id isnot found"));
        return userIdIsnotFound;
    }

    @Override
    public void deleteProject(Long projectId, Long userId) {
        Project projectIdIsNotFound = projectRepo.findById(projectId).orElseThrow(() -> new UsernameNotFoundException("Project id is not found"));
        projectRepo.delete(projectIdIsNotFound);
    }

    @Override
    public Project updateProject(Project project, Long projectId) {
        Project updateProject = projectRepo.findById(projectId).orElseThrow(() -> new UsernameNotFoundException("User id is not found"));
        updateProject.setName(project.getName());
        updateProject.setDescription(project.getDescription());
        updateProject.setTags(project.getTags());

        Project saveProject = projectRepo.save(updateProject);
        return saveProject;
    }

    @Override
    public void addUserToProject(Long projectId, Long userId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new UsernameNotFoundException("project id is not found"));
        User user = userService.findByUserId(userId);

        if(!project.getTeam().contains(user)){
            project.getChat().getUsers().add(user);
            project.getTeam().add(user);

        }

        projectRepo.save(project);

    }

    @Override
    public void removeUserToProject(Long projectId, Long userId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new UsernameNotFoundException("project id is not found"));
        User user = userService.findByUserId(userId);

        if(project.getTeam().contains(user)){
            project.getChat().getUsers().remove(user);
            project.getTeam().remove(user);

        }

        projectRepo.save(project);

    }

    @Override
    public Chat getChatByProjectId(Long projectId) {
        Project project = projectRepo.findById(projectId).orElseThrow(() -> new UsernameNotFoundException("project Id is not found"));


        return project.getChat();
    }

    @Override
    public List<Project> searchProject(String keyword, User user) {
        String partialName = "%" + keyword + "%";

        List<Project> projects = projectRepo.findByNameContainingAndTeamContaining(partialName, user);

        return projects;
    }
}
