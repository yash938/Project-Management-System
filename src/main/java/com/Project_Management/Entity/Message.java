package com.Project_Management.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long messageId;

    private String content;

    private LocalDate createdAt;

    @ManyToOne
    private Chat chat;

    @ManyToOne
    private User sender;
}
