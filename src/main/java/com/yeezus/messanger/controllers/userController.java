package com.yeezus.messanger.controllers;

import com.yeezus.messanger.configs.JwtCore;
import com.yeezus.messanger.entities.Chat;
import com.yeezus.messanger.entities.dto.*;
import com.yeezus.messanger.entities.users;
import com.yeezus.messanger.repositories.userRepository;
import com.yeezus.messanger.services.userService;
import com.yeezus.messanger.utils.ChatMapper;
import com.yeezus.messanger.utils.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
    private final userService userService;
    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;
    private final ChatMapper chatMapper;
    private final UserMapper userMapper;



    @PostMapping("/signUp")
    public ResponseEntity<?> singUp(@RequestBody createUserDto createUserDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().body(bindingResult.getFieldError().getDefaultMessage());
        } else {
            if(userRepository.existsByEmail(createUserDto.getEmail())) {
                return ResponseEntity.badRequest().body("Email Already Exist");
            }
            String encodedPassword = passwordEncoder.encode(createUserDto.getPassword());
            createUserDto.setPassword(encodedPassword);
            userService.registerUser(createUserDto);
            return ResponseEntity.ok("User Registered");
        }

    }


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody loginUserDto loginUserDto, BindingResult bindingResult) {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtCore.generateToken(auth);
        users LoginUser = jwtCore.getUserFromToken(jwt);
        ShareUserDto shareUserDto = new ShareUserDto();
        shareUserDto.setId(LoginUser.getId());
        shareUserDto.setUsername(LoginUser.getUsername());
        shareUserDto.setEmail(LoginUser.getEmail());
        shareUserDto.setBirthday(LoginUser.getBirthday());
        shareUserDto.setToken(jwt);
        Set<ChatDto>  chatDtos = new HashSet<>();
        for (Chat chat : LoginUser.getChats()) {
            chatDtos.add(chatMapper.toDTO(chat));
        }
        shareUserDto.setChats(chatDtos);


        return ResponseEntity.ok(shareUserDto);
    }

    @GetMapping("/getCurrentUser")
    public ResponseEntity<ShareUserDto> GetCurrentUser(@RequestHeader("Authorization") String token) {
        users CurrentUser = userRepository.findById(jwtCore.getIdFromToken(token)).get();
        ShareUserDto shareUserDto = new ShareUserDto();
        shareUserDto.setId(CurrentUser.getId());
        shareUserDto.setUsername(CurrentUser.getUsername());
        shareUserDto.setEmail(CurrentUser.getEmail());
        shareUserDto.setBirthday(CurrentUser.getBirthday());
        shareUserDto.setToken(token);
        Set<ChatDto> chats = new HashSet<>();
        for (Chat chat: CurrentUser.getChats()) {
            chats.add(chatMapper.toDTO(chat));
        }
        shareUserDto.setChats(chats);
        return ResponseEntity.ok(shareUserDto);
    }

    @GetMapping("/getUserByUsername")
    public ResponseEntity<UserDto> getUserByUsername(@RequestParam String username) {
        users user = userRepository.findByUsername(username);
        if( user == null ) {
            return ResponseEntity.notFound().build();
        }
        UserDto userDto = userMapper.toDTO(user);
        return ResponseEntity.ok(userDto);
    }


}
