package com.Project_Management.Service;

import jakarta.mail.MessagingException;

public interface EmailService {
    void sendEmailWithToken(String userEmail,String link) throws MessagingException;
}
