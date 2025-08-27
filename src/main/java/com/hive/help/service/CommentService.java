package com.hive.help.service;

import com.hive.help.dto.comment.*;
import org.springframework.data.domain.*;

public interface CommentService {
    CommentDto add(Long ticketId, Long authorId, CommentCreateDto dto);
    Page<CommentDto> list(Long ticketId, int page, int size);
}
