package com.Project_Management.Dtos;

import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectDto {
    private String name;
    private String description;
    private String category;
    private List<String> tags;
    private User owner;
    private List<User> team;
    private Chat chat;
}
