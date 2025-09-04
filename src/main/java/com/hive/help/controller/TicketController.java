package com.hive.help.controller;

import com.hive.help.dto.ticket.*;
import com.hive.help.service.TicketService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/tickets")
@PreAuthorize("isAuthenticated()") // default: all ticket endpoints require auth
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    // Create ticket: any authenticated user
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    @PostMapping
    public TicketDto create(@RequestParam Long creatorId, @RequestBody TicketCreateDto dto) {
        return service.create(creatorId, dto);
    }

    // Get ticket by id: any authenticated user (owner/assignee checks can be added later if needed)
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN','USER')")
    @GetMapping("/{id}")
    public TicketDto get(@PathVariable Long id) {
        return service.get(id);
    }

    // List all tickets: technician/supervisor/admin
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping
    public Page<TicketDto> list(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        return service.listAll(page, size);
    }

    // By creator: allow users + elevated roles (owner-only restriction can be added once principal exposes userId)
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/by-creator/{creatorId}")
    public Page<TicketDto> byCreator(@PathVariable Long creatorId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return service.listByCreator(creatorId, page, size);
    }

    // By assignee: technician/supervisor/admin
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/by-assignee/{techId}")
    public Page<TicketDto> byAssignee(@PathVariable Long techId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return service.listByAssignee(techId, page, size);
    }

    // Update: technician/supervisor/admin
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @PutMapping("/{id}")
    public TicketDto update(@PathVariable Long id, @RequestBody TicketUpdateDto dto) {
        return service.update(id, dto);
    }

    // Update status: technician/supervisor/admin
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @PatchMapping("/{id}/status")
    public TicketDto updateStatus(@PathVariable Long id, @RequestBody TicketStatusUpdateDto dto) {
        return service.updateStatus(id, dto);
    }

    // Assign technician: supervisor/admin
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @PatchMapping("/{id}/assign")
    public TicketDto assign(@PathVariable Long id, @RequestBody TicketAssignDto dto) {
        return service.assign(id, dto);
    }

    // Delete: admin only
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // Traffic-light views: technician/supervisor/admin
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/green")
    public Page<TicketDto> green(@RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size) {
        return service.listGreen(page, size);
    }

    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/orange")
    public Page<TicketDto> orange(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "10") int size) {
        return service.listOrange(page, size);
    }

    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/red")
    public Page<TicketDto> red(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return service.listRed(page, size);
    }
}
