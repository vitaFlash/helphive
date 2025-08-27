package com.hive.help.service.impl;

import com.hive.help.dto.comment.*;
import com.hive.help.exception.NotFoundException;
import com.hive.help.mapper.CommentMapper;
import com.hive.help.model.*;
import com.hive.help.repository.*;
import com.hive.help.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository repo;
    private final TicketRepository ticketRepo;
    private final UserRepository userRepo;
    private final CommentMapper mapper;

    @Override
    public CommentDto add(Long ticketId, Long authorId, CommentCreateDto dto) {
        Ticket t = ticketRepo.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));
        User u = userRepo.findById(authorId).orElseThrow(() -> new NotFoundException("Author not found"));
        Comment c = mapper.toEntity(dto);
        c.setTicket(t);
        c.setAuthor(u);
        return mapper.toDto(repo.save(c));
    }

    @Override
    public Page<CommentDto> list(Long ticketId, int page, int size) {
        return repo.findByTicketId(ticketId, PageRequest.of(page, size, Sort.by("createdAt").ascending()))
                .map(mapper::toDto);
    }
}
