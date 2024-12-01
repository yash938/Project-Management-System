package com.Project_Management.ServiceImpl;

import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.Message;
import com.Project_Management.Entity.Project;
import com.Project_Management.Entity.User;
import com.Project_Management.Repository.MessageRepo;
import com.Project_Management.Repository.UserRepo;
import com.Project_Management.Service.MessageService;
import com.Project_Management.Service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ProjectService projectService;
    @Override
    public Message sendMessage(Long senderId, Long projectId, String content) {
        User user = userRepo.findById(senderId).orElseThrow(() -> new RuntimeException("user id is not found"));
        Chat chat = projectService.getProjectById(projectId).getChat();
        Message message = new Message();
        message.setContent(content);
        message.setSender(user);
        message.setContent(String.valueOf(LocalDate.now()));
        message.setChat(chat);
        Message createMessage = messageRepo.save(message);
        return createMessage;
    }

    @Override
    public List<Message> messageByProjectId(Long projectId) {
        Chat chat = projectService.getChatByProjectId(projectId);
        List<Message> message = messageRepo.findByChatIdOrderByCreatedAtAsc(chat.getChatId());
        return message;
    }
}
