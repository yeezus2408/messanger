package com.yeezus.messanger.controllers;

import com.yeezus.messanger.configs.JwtCore;
import com.yeezus.messanger.entities.dto.createUserDto;
import com.yeezus.messanger.entities.dto.loginUserDto;
import com.yeezus.messanger.repositories.userRepository;
import com.yeezus.messanger.services.userService;
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

@Controller
@RequestMapping("/user")
@RequiredArgsConstructor
public class userController {
    private final userService userService;
    private final userRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtCore jwtCore;



    @PostMapping("/singUp")
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
    public ResponseEntity<String> login(@RequestBody loginUserDto loginUserDto, BindingResult bindingResult) {
        Authentication auth = null;
        try {
            auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
        SecurityContextHolder.getContext().setAuthentication(auth);
        String jwt = jwtCore.generateToken(auth);
        return ResponseEntity.ok(jwt);
    }

}
