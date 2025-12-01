package com.yeezus.messanger.repositories;

import com.yeezus.messanger.entities.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {
}
