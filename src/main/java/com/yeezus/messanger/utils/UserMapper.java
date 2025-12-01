package com.yeezus.messanger.utils;

import com.yeezus.messanger.entities.dto.UserDto;
import com.yeezus.messanger.entities.users;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserDto toDTO(users user) {
        if (user == null) return null;
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setBirthdate(user.getBirthday());

        return userDto;
    }

}
