package com.yeezus.messanger.services;

import com.yeezus.messanger.configs.JwtCore;
import com.yeezus.messanger.entities.Chat;
import com.yeezus.messanger.entities.Enum.ChatType;
import com.yeezus.messanger.entities.dto.ChatDto;
import com.yeezus.messanger.entities.message;
import com.yeezus.messanger.entities.users;
import com.yeezus.messanger.repositories.ChatRepository;
import com.yeezus.messanger.repositories.messageRepository;
import com.yeezus.messanger.repositories.userRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class chatService {
    private final JwtCore jwtcore;
    private final userRepository userRepository;
    private final ChatRepository ChatRepository;
    private final messageRepository messageRepository;
    private final messageService messageService;

    public chatService(JwtCore jwtcore, userRepository userRepository, ChatRepository ChatRepository, messageRepository messageRepository, messageService messageService) {
        this.jwtcore = jwtcore;
        this.userRepository = userRepository;
        this.ChatRepository = ChatRepository;
        this.messageRepository = messageRepository;
        this.messageService = messageService;
    }

    @Transactional
    public ResponseEntity<?> createPrivateChat(Long senderId, Long receiverId, String content) {
        if (userRepository.existsById(senderId) && userRepository.existsById(receiverId)) {
            Chat newPrivateChat = new Chat();
            newPrivateChat.getMembers().add(userRepository.findById(senderId).get());
            newPrivateChat.getMembers().add(userRepository.findById(receiverId).get());
            newPrivateChat.setType(ChatType.PRIVATE);
            ChatRepository.save(newPrivateChat);

            message newMessage = new message();
            newMessage.setContent(content);
            newMessage.setSender(userRepository.findById(senderId).get());
            //newMessage.setRecipientChat(newPrivateChat);
            messageRepository.save(newMessage);

            return ResponseEntity.ok("Private chat created successfully.");
        } else {
            return ResponseEntity.badRequest().body("One or both users do not exist.");
        }
    }

    public Chat createGroupChat(ChatDto dataChat) {
        List<users> members = userRepository.findAllById(dataChat.getMembersIds());
        if(members.size() != dataChat.getMembersIds().size()){
            throw new IllegalArgumentException("One or more user IDs are invalid.");
        }
        Chat newGroupChat = new Chat();
        newGroupChat.setName(dataChat.getName());
        newGroupChat.setMembers(members);
        newGroupChat.setType(ChatType.GROUP);

        return newGroupChat;
    }
}
