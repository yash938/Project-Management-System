package com.Project_Management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {


    private String fullName;
    private String password;

    private String email;
    private String projectSize;

    private IssueDto issueDto;
}
