package com.yeezus.messanger.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class chat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @Column(name = "members_id")
    private Set<Long> members_id = new HashSet<>();

    @ElementCollection
    @Column(name = "admins")
    private Set<Long> admins = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private users owner;
}
