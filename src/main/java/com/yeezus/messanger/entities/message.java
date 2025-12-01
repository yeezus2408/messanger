package com.yeezus.messanger.entities;

import com.yeezus.messanger.configs.JwtCore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String content;
    private LocalDateTime timestamp = LocalDateTime.now();

    @Column(nullable = false)
    private Long recipientChatId;

    @ManyToOne
    @JoinColumn(name = "sender_user_id")
    private users sender;

}
