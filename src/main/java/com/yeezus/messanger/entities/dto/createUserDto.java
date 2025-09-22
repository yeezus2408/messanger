package com.yeezus.messanger.entities.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class createUserDto {
    private String username;
    private String password;
    private String email;
    private Date birthday;

}
