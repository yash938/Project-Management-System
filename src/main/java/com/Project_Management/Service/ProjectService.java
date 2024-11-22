package com.Project_Management.Service;

import com.Project_Management.Dtos.ChatDto;
import com.Project_Management.Dtos.ProjectDto;
import com.Project_Management.Dtos.UserDto;
import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;

import java.util.List;

public interface ProjectService {
    ProjectDto createProject(ProjectDto projectDto);
    List<ProjectDto> getProjectByTeam(User user, String category, String tags);

    List<ProjectDto> getProjectById(Long projectId);

    void deleteProject(Long projectId);

    ProjectDto updateProject(ProjectDto projectDto,Long projectId);

    void addUserToProject(Long projectId,Long userId);

    void removeUserToProject(Long projectId,Long userId);

    ChatDto getChatByProjectId(Long projectId);

    List<ProjectDto> searchProject(String keyword, UserDto user);

}
