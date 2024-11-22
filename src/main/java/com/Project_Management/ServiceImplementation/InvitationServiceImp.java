package com.Project_Management.ServiceImplementation;

import com.Project_Management.Dtos.InvitationDto;
import com.Project_Management.Entity.Invitation;
import com.Project_Management.Repository.InvitationRepo;
import com.Project_Management.Service.EmailService;
import com.Project_Management.Service.InvitationService;
import jakarta.mail.MessagingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class InvitationServiceImp implements InvitationService {

    @Autowired
    private InvitationRepo invitationRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public void sendInvitation(String email, Long projectId) throws MessagingException {

        String token = UUID.randomUUID().toString();
        Invitation invitationDto = new Invitation();
        invitationDto.setEmail(email);
        invitationDto.setToken(token);
        invitationDto.setProjectId(projectId);

        invitationRepo.save(invitationDto);

        String invitationLink = "http://localhost:5173/accept_invitaion?token="+token;
        emailService.sendEmailWithToken(email,invitationLink);

    }

    @Override
    public InvitationDto acceptInvitation(String token, Long userId) throws Exception {
        InvitationDto byToken = invitationRepo.findByToken(token);
        if(byToken == null){
            throw new Exception("Invalid invitation token");
        }
        return byToken;
    }

    @Override
    public String getTokenByUserMail(String userEmail) {

        InvitationDto byEmail = invitationRepo.findByEmail(userEmail);

        return byEmail.getToken();
    }

    @Override
    public void deleteToken(String token) {

        InvitationDto byToken = invitationRepo.findByToken(token);
        if(byToken == null){
            throw new RuntimeException("Invitation not found for the provided token.");
        }

        invitationRepo.deleteByToken(token);

    }
}
