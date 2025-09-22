package com.yeezus.messanger.repositories;

import com.yeezus.messanger.entities.users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface userRepository extends JpaRepository<users, Long> {
    users findByEmail(String email);
    users findByUsername(String username);
    boolean existsByEmail(String email);
}
