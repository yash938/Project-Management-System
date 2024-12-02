package com.Project_Management.Entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RefreshToken {
    @Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private int id;
    private String token;
    private Instant expiryDate;

    @OneToOne
    private User user;

}
