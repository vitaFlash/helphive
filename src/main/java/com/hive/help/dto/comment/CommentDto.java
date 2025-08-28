package com.hive.help.dto.comment;

import java.time.LocalDateTime;

public record CommentDto(
        Long id,
        String body,
        Long authorId,
        Long ticketId,
        LocalDateTime createdAt
) {}