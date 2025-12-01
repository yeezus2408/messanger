package com.yeezus.messanger.entities.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class newMessageDto {
    private String content;
    private Long recipient_chat;
}
