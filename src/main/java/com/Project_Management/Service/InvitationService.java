package com.Project_Management.Service;

import com.Project_Management.Dtos.InvitationDto;
import jakarta.mail.MessagingException;

public interface InvitationService {
    public void sendInvitation(String email,Long projectId) throws MessagingException;
    public InvitationDto acceptInvitation(String token,Long userId) throws Exception;
    public String getTokenByUserMail(String userEmail);

    void deleteToken(String token);
}
