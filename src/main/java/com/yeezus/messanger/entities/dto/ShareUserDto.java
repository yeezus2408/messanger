package com.yeezus.messanger.entities.dto;

import com.yeezus.messanger.entities.Chat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class ShareUserDto {
    private Long id;
    private String username;
    private String email;
    private Date birthday;
    private Set<ChatDto> chats;
    private String token;
}
