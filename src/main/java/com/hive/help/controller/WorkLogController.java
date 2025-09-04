package com.hive.help.controller;

import com.hive.help.dto.worklog.*;
import com.hive.help.service.WorkLogService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/tickets/{ticketId}/worklogs")
@PreAuthorize("isAuthenticated()")
@RequiredArgsConstructor
public class WorkLogController {

    private final WorkLogService service;

    // Add a worklog: technicians, supervisors, admins
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @PostMapping
    public WorkLogDto add(@PathVariable Long ticketId,
                          @RequestParam Long technicianId,
                          @RequestBody WorkLogCreateDto dto) {
        return service.add(ticketId, technicianId, dto);
    }

    // List worklogs for a ticket: allow all authenticated roles to view
    // (tighten later to owner/assignee if you expose principal.id)
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping
    public Page<WorkLogDto> list(@PathVariable Long ticketId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return service.listByTicket(ticketId, page, size);
    }
}
