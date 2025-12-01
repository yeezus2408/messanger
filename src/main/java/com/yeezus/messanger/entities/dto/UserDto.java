package com.yeezus.messanger.entities.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Date birthdate;
}
