package com.yeezus.messanger.utils;

import com.yeezus.messanger.entities.dto.MessageDto;
import com.yeezus.messanger.entities.dto.UserDto;
import com.yeezus.messanger.entities.message;
import jakarta.persistence.Column;
import org.springframework.stereotype.Component;

@Component
public class MessageMapper {
    private final UserMapper userMapper;

    public MessageMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public MessageDto toMessageDto(message message) {
        if(message == null) return null;

        MessageDto messageDto = new MessageDto();
        messageDto.setId(message.getId());
        messageDto.setSenderId(userMapper.toDTO(message.getSender()));
        messageDto.setTimestamp(message.getTimestamp());
        messageDto.setContent(message.getContent());
        messageDto.setRecipientChatId(message.getRecipientChatId());
        return messageDto;
    }
}
