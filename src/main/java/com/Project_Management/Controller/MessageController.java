package com.Project_Management.Controller;

import com.Project_Management.Dto.MessageDto;
import com.Project_Management.Dto.UserDto;
import com.Project_Management.Entity.Chat;
import com.Project_Management.Entity.Message;
import com.Project_Management.Service.MessageService;
import com.Project_Management.Service.ProjectService;
import com.Project_Management.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/message")
public class MessageController {
    @Autowired
    private MessageService messageService;
    @Autowired
    private UserService userService;
    @Autowired
    private ProjectService projectService;

    @PostMapping
    public ResponseEntity<Message> sendMessage(@RequestBody MessageDto messageDto){
        UserDto user = userService.findByUserId(messageDto.getSenderId());
        Chat chat = projectService.getProjectById(messageDto.getProjectId()).getChat();
        Message message = messageService.sendMessage(messageDto.getProjectId(), messageDto.getSenderId(), messageDto.getContent());
        return new ResponseEntity<>(message, HttpStatus.OK);

    }

    @GetMapping("/chat/{projectId}")
    public ResponseEntity<List<Message>> getMessageByChatId(@PathVariable Long projectId){
        List<Message> messages = messageService.messageByProjectId(projectId);
        return new ResponseEntity<>(messages, HttpStatus.OK);
    }
}
