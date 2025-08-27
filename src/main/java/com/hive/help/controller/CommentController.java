package com.hive.help.controller;

import com.hive.help.dto.comment.*;
import com.hive.help.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets/{ticketId}/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService service;

    @PostMapping
    public CommentDto add(@PathVariable Long ticketId,
                          @RequestParam Long authorId,
                          @RequestBody CommentCreateDto dto) {
        return service.add(ticketId, authorId, dto);
    }

    @GetMapping
    public Page<CommentDto> list(@PathVariable Long ticketId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return service.list(ticketId, page, size);
    }
}
