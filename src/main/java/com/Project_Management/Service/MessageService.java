package com.Project_Management.Service;

import com.Project_Management.Entity.Message;

import java.util.List;

public interface MessageService {
    Message sendMessage(Long senderId,Long projectId,String content);
    List<Message> messageByProjectId(Long projectId);

}
