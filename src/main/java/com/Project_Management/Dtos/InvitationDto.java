package com.Project_Management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InvitationDto {
    private String token;
    private String email;
    private Long projectId;
}
