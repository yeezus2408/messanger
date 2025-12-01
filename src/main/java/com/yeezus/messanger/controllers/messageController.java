package com.yeezus.messanger.controllers;


import com.yeezus.messanger.entities.dto.MessageDto;
import com.yeezus.messanger.entities.dto.newMessageDto;
import com.yeezus.messanger.entities.message;
import com.yeezus.messanger.repositories.messageRepository;
import com.yeezus.messanger.services.messageService;
import com.yeezus.messanger.utils.MessageMapper;
import org.aspectj.bridge.Message;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/messages")
public class messageController {
    private final messageService messageService;
    private final messageRepository messageRepository;
    private final MessageMapper  messageMapper;


    public messageController(messageService messageService, messageRepository messageRepository, MessageMapper messageMapper) {
        this.messageService = messageService;
        this.messageRepository = messageRepository;
        this.messageMapper = messageMapper;
    }


    @PostMapping("/sendMessage")
    public ResponseEntity<?> sendMessage(@RequestBody newMessageDto newMessageDto, @RequestHeader("Authorization") String token) {
        return messageService.sendMessage(newMessageDto, token);
    }


    @GetMapping("/GetMessagesByChatId")
    public ResponseEntity<List<MessageDto>> getMessagesByChatId(@RequestParam Long id) {
        List<message> messages = messageRepository.findByRecipientChatId(id);
        List<MessageDto> messageDtos = new ArrayList<>();
        for  (message message : messages) {
            messageDtos.add(messageMapper.toMessageDto(message));
        }
        return ResponseEntity.ok(messageDtos);

    }
}
