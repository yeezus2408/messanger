package com.yeezus.messanger.services;


import com.yeezus.messanger.entities.dto.createUserDto;
import com.yeezus.messanger.entities.users;
import com.yeezus.messanger.repositories.userRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class userService {
    private final userRepository userRepository;

    public userService(com.yeezus.messanger.repositories.userRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<?> registerUser(createUserDto createUserDto) {
        users newUsers = new users();
        newUsers.setUsername(createUserDto.getUsername());
        newUsers.setPassword(createUserDto.getPassword());
        newUsers.setEmail(createUserDto.getEmail());
        newUsers.setBirthday(createUserDto.getBirthday());
        userRepository.save(newUsers);
        return ResponseEntity.ok("New user created");
    }
}
