package com.yeezus.messanger.entities.dto;

import com.yeezus.messanger.entities.Enum.ChatType;
import com.yeezus.messanger.entities.message;
import lombok.Getter;
import lombok.Setter;
import org.aspectj.bridge.Message;

import java.util.List;

@Getter
@Setter
public class ChatDto {
    private Long id;
    private ChatType type;
    private String name;
    private List<Long> membersIds;
    private Long ownerId;
    private List<Long> admins;
    private List<MessageDto> messages;

}
