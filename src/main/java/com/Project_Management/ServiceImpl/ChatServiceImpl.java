package com.Project_Management.ServiceImpl;

import com.Project_Management.Entity.Chat;
import com.Project_Management.Repository.ChatRepo;
import com.Project_Management.Service.ChatService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    private ChatRepo chatRepo;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public Chat createChat(Chat chat) {
        Chat save = chatRepo.save(chat);
        return save;
    }
}
