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
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    // Create a ticket (authenticated users)
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    @PostMapping
    public TicketDto create(@RequestParam Long creatorId, @RequestBody TicketCreateDto dto) {
        return service.create(creatorId, dto);
    }

    // Get a ticket by ID
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/{id}")
    public TicketDto get(@PathVariable Long id) {
        return service.get(id);
    }

    // List all tickets (technician/supervisor/admin)
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping
    public Page<TicketDto> listAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return service.listAll(page, size);
    }

    // List tickets by creator
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/by-creator/{creatorId}")
    public Page<TicketDto> listByCreator(@PathVariable Long creatorId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return service.listByCreator(creatorId, page, size);
    }

    // List tickets by assignee
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/by-assignee/{techId}")
    public Page<TicketDto> listByAssignee(@PathVariable Long techId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return service.listByAssignee(techId, page, size);
    }

    // Update ticket details
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @PutMapping("/{id}")
    public TicketDto update(@PathVariable Long id, @RequestBody TicketUpdateDto dto) {
        return service.update(id, dto);
    }

    // Update ticket status
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @PatchMapping("/{id}/status")
    public TicketDto updateStatus(@PathVariable Long id, @RequestBody TicketStatusUpdateDto dto) {
        return service.updateStatus(id, dto);
    }

    // Assign a technician
    @PreAuthorize("hasAnyRole('SUPERVISOR','ADMIN')")
    @PatchMapping("/{id}/assign")
    public TicketDto assign(@PathVariable Long id, @RequestBody TicketAssignDto dto) {
        return service.assign(id, dto);
    }

    // Delete a ticket (admin only)
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // Traffic-light views
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/green")
    public Page<TicketDto> listGreen(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return service.listGreen(page, size);
    }

    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/orange")
    public Page<TicketDto> listOrange(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return service.listOrange(page, size);
    }

    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    @GetMapping("/red")
    public Page<TicketDto> listRed(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return service.listRed(page, size);
    }
}
