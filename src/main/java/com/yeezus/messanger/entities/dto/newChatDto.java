package com.yeezus.messanger.entities.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class newChatDto {
    private String name;
    private List<Long> membersIds;
    private List<MessageDto> messages;
}
