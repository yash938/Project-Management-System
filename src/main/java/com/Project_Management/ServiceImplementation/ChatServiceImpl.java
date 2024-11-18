package com.Project_Management.ServiceImplementation;

import com.Project_Management.Entity.Chat;
import com.Project_Management.Repository.ChatRepo;
import com.Project_Management.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {
    @Autowired
    private ChatRepo chatRepo;
    @Override
    public Chat createChat(Chat chat) {

        return chatRepo.save(chat);
    }
}
