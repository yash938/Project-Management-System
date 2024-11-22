package com.Project_Management.Dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChatDto {
    private String name;
    private Long projectId;
    private List<Long> message;
    private List<Long> userIds;
}
