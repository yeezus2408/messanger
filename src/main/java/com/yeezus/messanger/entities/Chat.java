package com.yeezus.messanger.entities;


import com.yeezus.messanger.entities.Enum.ChatType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatType type;

    @Column(nullable = false)
    private String name;

    @ManyToMany
    @JoinTable(
            name = "chat_members",
            joinColumns = @JoinColumn(name = "chat_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<users> members;

    @ElementCollection
    @Column(name = "admins")
    private List<users> admins = new ArrayList<>();

    @OneToMany(mappedBy = "recipientChatId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<message> messages = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private users owner;
}
