package com.Project_Management.Service;


import com.Project_Management.Dto.UserDto;
import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;

import java.util.List;

public interface ProjectService {
    Project createProject(Project project,User user);
    List<Project> getProjectByTeam(User user, String category, String tags);

    Project getProjectById(Long projectId);

    void deleteProject(Long projectId);

    Project updateProject(Project project,Long projectId);

    void addUserToProject(Long projectId,Long userId);

    void removeUserToProject(Long projectId,Long userId);

    Chat getChatByProjectId(Long projectId);

    List<Project> searchProject(String keyword, User user);

}
