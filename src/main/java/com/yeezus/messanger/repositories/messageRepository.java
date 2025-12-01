package com.yeezus.messanger.repositories;

import com.yeezus.messanger.entities.message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface messageRepository extends JpaRepository<message, Long> {
    List<message> findByRecipientChatId(Long id);
}
