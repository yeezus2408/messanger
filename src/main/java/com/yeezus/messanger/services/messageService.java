package com.yeezus.messanger.services;

import com.yeezus.messanger.configs.JwtCore;
import com.yeezus.messanger.entities.dto.newMessageDto;
import com.yeezus.messanger.entities.message;
import com.yeezus.messanger.entities.users;
import com.yeezus.messanger.repositories.messageRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class messageService {
    private final messageRepository messageRepository;
    private final JwtCore jwtCore;

    public messageService(messageRepository messageRepository, JwtCore jwtCore) {
        this.messageRepository = messageRepository;
        this.jwtCore = jwtCore;
    }


    public ResponseEntity<?> sendMessage(newMessageDto newMessageDto, String token) {
        message newMessage = new message();
        users user = jwtCore.getUserFromToken(token);
        newMessage.setContent(newMessageDto.getContent());
        newMessage.setSender(user);
        newMessage.setRecipientChatId(newMessageDto.getRecipient_chat());
        newMessage.setTimestamp(LocalDateTime.now());
        messageRepository.save(newMessage);
        return ResponseEntity.ok("Message sent successfully.");
    }

}
