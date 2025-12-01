package com.yeezus.messanger.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true, nullable = false, name = "user_id")
    private Long id;

    @Column(unique=true, name = "username")
    private String username;
    @Column(name = "password")
    private String password;
    @Column(unique=true, name = "email")
    private String email;
    @Column(name = "birthday", nullable = true)
    private Date birthday;
    @ManyToMany
    @JoinTable(
            name = "users_chats",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "chat_id")
    )
    private Set<Chat> Chats = new HashSet<Chat>();

}
