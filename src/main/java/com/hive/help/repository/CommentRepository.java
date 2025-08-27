package com.hive.help.repository;

import com.hive.help.model.Comment;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Page<Comment> findByTicketId(Long ticketId, Pageable pageable);
}