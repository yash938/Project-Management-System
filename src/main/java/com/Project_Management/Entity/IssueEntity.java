package com.Project_Management.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class IssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long issueId;

    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private User user;
}
