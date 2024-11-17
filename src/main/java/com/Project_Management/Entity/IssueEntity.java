package com.Project_Management.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long issueId;
}
