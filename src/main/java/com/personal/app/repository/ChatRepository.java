package com.personal.app.repository;

import com.personal.app.models.entities.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ChatRepository extends JpaRepository<Chat, Long>, JpaSpecificationExecutor<Chat> {
    Page<Chat> findByName(Pageable pageable, String name);
}
