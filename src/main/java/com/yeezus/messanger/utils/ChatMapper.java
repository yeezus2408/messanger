package com.yeezus.messanger.utils;

import com.yeezus.messanger.entities.Chat;
import com.yeezus.messanger.entities.dto.ChatDto;
import com.yeezus.messanger.entities.dto.MessageDto;
import com.yeezus.messanger.entities.dto.UserDto;
import com.yeezus.messanger.entities.message;
import com.yeezus.messanger.entities.users;
import com.yeezus.messanger.utils.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ChatMapper {
    private final MessageMapper messageMapper;

    public ChatMapper(MessageMapper messageMapper) {
        this.messageMapper = messageMapper;
    }

    public ChatDto toDTO(Chat chat) {
        if (chat == null) return null;



        List<Long> memberIds = chat.getMembers() != null
                ? chat.getMembers().stream()
                .map(users::getId)
                .toList()
                : List.of();

        List<Long> adminIds = chat.getAdmins() != null
                ? chat.getAdmins().stream()
                .map(users::getId)
                .toList()
                : List.of();

        List<MessageDto> messages = new ArrayList<>();
        for(message Message: chat.getMessages()){
            messages.add(messageMapper.toMessageDto(Message));
        }


        ChatDto chatDto = new ChatDto();
        chatDto.setId(chat.getId());
        chatDto.setName(chat.getName());
        chatDto.setType(chat.getType());
        chatDto.setMembersIds(memberIds);
        chatDto.setOwnerId(chat.getOwner().getId());
        chatDto.setAdmins(adminIds);
        chatDto.setMessages(messages);

        return chatDto;
    }
}
