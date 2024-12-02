package com.Project_Management.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class IssueReq {
    private Long issueId;
    private String title;
    private String description;
    private String status;
    private Long projectId;
    private String priority;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dueDate;
    private List<String> tags = new ArrayList<>();
    private Project project;
    private User assignee;
}
