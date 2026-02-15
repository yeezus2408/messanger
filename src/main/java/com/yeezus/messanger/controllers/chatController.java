package com.yeezus.messanger.controllers;

import com.yeezus.messanger.configs.JwtCore;
import com.yeezus.messanger.entities.Chat;
import com.yeezus.messanger.entities.dto.ChatDto;
import com.yeezus.messanger.entities.dto.MessageDto;
import com.yeezus.messanger.entities.users;
import com.yeezus.messanger.repositories.ChatRepository;
import com.yeezus.messanger.repositories.userRepository;
import com.yeezus.messanger.services.chatService;
import com.yeezus.messanger.utils.ChatMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/chat")
public class chatController {
    @Autowired
    private final chatService chatService;
    private final JwtCore jwtCore;
    private final userRepository userRepository;
    private final ChatRepository chatRepository;
    private final ChatMapper chatMapper;

    public chatController(chatService chatService, JwtCore jwtCore, userRepository userRepository, ChatRepository chatRepository, ChatMapper chatMapper) {
        this.chatService = chatService;
        this.jwtCore = jwtCore;
        this.userRepository = userRepository;
        this.chatRepository = chatRepository;
        this.chatMapper = chatMapper;
    }



    @GetMapping("/GetAll")
    public ResponseEntity<List<ChatDto>> getAllChats() {
        List<Chat> chats = chatRepository.findAll();
        List<ChatDto> chatDtos = new ArrayList<>();
        for(Chat chat : chats) {
            chatDtos.add(chatMapper.toDTO(chat));
        }
        return ResponseEntity.ok(chatDtos);
    }

    @PostMapping("/createGroupChat")
    public ResponseEntity<?> createGroupChat(@RequestBody ChatDto ChatDto, @RequestHeader("Authorization") String token) {
        Chat newChat = chatService.createGroupChat(ChatDto);
        users creator = userRepository.findById(jwtCore.getIdFromToken(token)).get();
        newChat.getMembers().add(creator);
        newChat.setOwner(creator);
        chatRepository.save(newChat);

        List<users> newMembers = newChat.getMembers();
        for (users user : newMembers) {
            user.getChats().add(newChat);
            userRepository.save(user);
        }


        return ResponseEntity.ok("Group chat created successfully.");
    }


//    @PostMapping("/createPrivateChat")
//    public ResponseEntity<?> createPrivateChat(@RequestBody MessageDto messageDto, @RequestHeader("Authorization") String token) {
//        //todo
//    }

    @GetMapping("/GetChatsByCurrentUser")
    public ResponseEntity<List<ChatDto>> getChatsByCurrentUser(@RequestHeader("Authorization") String token) {
        Optional<users> CurrentUser = userRepository.findById(jwtCore.getIdFromToken(token));
        Set<Chat> ChatsByUser = CurrentUser.get().getChats();
        List<ChatDto> chatDtos = new ArrayList<>();
        for(Chat chat : ChatsByUser) {
            chatDtos.add(chatMapper.toDTO(chat));
        }



        return  ResponseEntity.ok(chatDtos);
    }
}
