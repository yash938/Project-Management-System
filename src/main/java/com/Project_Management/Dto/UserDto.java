package com.Project_Management.Dto;

import com.Project_Management.Entity.IssueEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private Long userId;

    private String fullName;
    private String password;

    private String email;
    private String projectSize;

    private List<IssueEntity> assignIssue ;
}
