package com.Project_Management.Repository;

import com.Project_Management.Dtos.InvitationDto;
import com.Project_Management.Entity.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvitationRepo extends JpaRepository<Invitation,Long> {
    InvitationDto findByToken(String token);
    InvitationDto findByEmail(String userEmail);

    void deleteByToken(String token);
}
