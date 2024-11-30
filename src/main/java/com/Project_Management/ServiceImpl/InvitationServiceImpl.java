package com.Project_Management.ServiceImpl;

import com.Project_Management.Entity.Invitation;
import com.Project_Management.Repository.InvitationRepo;
import com.Project_Management.Service.EmailService;
import com.Project_Management.Service.InvitationService;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImpl implements InvitationService {
    @Autowired
    private InvitationRepo invitationRepo;
    @Autowired
    private EmailService emailService;
    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {
        String invitationToken = UUID.randomUUID().toString();

        Invitation invitation = new Invitation();
        invitation.setEmail(email);
        invitation.setProjectId(projectId);
        invitation.setToken(invitationToken);
        invitationRepo.save(invitation);

        String invitationLink = "http://localhost:5173/acceptInvite?token="+invitationToken;
        emailService.sendEmailWithToken(email,invitationLink);
    }

    @Override
    public Invitation acceptInvitation(String token, Long userId) throws Exception {

        Invitation byToken = invitationRepo.findByToken(token);
        if(byToken == null){
            throw new Exception("Invitation invalid token");
        }
        return byToken;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {
        Invitation byEmail = invitationRepo.findByEmail(userEmail);
        return byEmail.getToken();
    }

    @Override
    public void deleteToken(String token) {
        Invitation byToken = invitationRepo.findByToken(token);
        invitationRepo.delete(byToken);
    }
}
