package com.hive.help.controller;

import com.hive.help.dto.worklog.*;
import com.hive.help.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets/{ticketId}/worklogs")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogService service;

    @PostMapping
    public WorkLogDto add(@PathVariable Long ticketId,
                          @RequestParam Long technicianId,
                          @RequestBody WorkLogCreateDto dto) {
        return service.add(ticketId, technicianId, dto);
    }

    @GetMapping
    public Page<WorkLogDto> list(@PathVariable Long ticketId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return service.listByTicket(ticketId, page, size);
    }
}

