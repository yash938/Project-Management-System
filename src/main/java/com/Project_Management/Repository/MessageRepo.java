package com.Project_Management.Repository;

import com.Project_Management.Entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepo extends JpaRepository<Message ,Long> {
    List<Message> findByChatIdOrderByCreatedAtAsc(Long chatId);
}
