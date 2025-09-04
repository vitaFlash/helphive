package com.hive.help.controller;

import com.hive.help.dto.ticket.*;
import com.hive.help.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    // ---- CREATE ----
    @PostMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public TicketDto create(@RequestParam Long creatorId,
                            @RequestBody TicketCreateDto dto) {
        return service.create(creatorId, dto);
    }

    // ---- READ ----
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    public TicketDto get(@PathVariable Long id) {
        return service.get(id);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    public Page<TicketDto> listAll(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return service.listAll(page, size);
    }

    @GetMapping("/creator/{creatorId}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public Page<TicketDto> listByCreator(@PathVariable Long creatorId,
                                         @RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "10") int size) {
        return service.listByCreator(creatorId, page, size);
    }

    @GetMapping("/technician/{technicianId}")
    @PreAuthorize("hasRole('TECHNICIAN') or hasRole('ADMIN')")
    public Page<TicketDto> listByAssignee(@PathVariable Long technicianId,
                                          @RequestParam(defaultValue = "0") int page,
                                          @RequestParam(defaultValue = "10") int size) {
        return service.listByAssignee(technicianId, page, size);
    }

    // ---- UPDATE ----
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    public TicketDto update(@PathVariable Long id,
                            @RequestBody TicketUpdateDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('TECHNICIAN','SUPERVISOR','ADMIN')")
    public TicketDto updateStatus(@PathVariable Long id,
                                  @RequestBody TicketStatusUpdateDto dto) {
        return service.updateStatus(id, dto);
    }

    @PatchMapping("/{id}/assign")
    @PreAuthorize("hasRole('SUPERVISOR') or hasRole('ADMIN')")
    public TicketDto assign(@PathVariable Long id,
                            @RequestBody TicketAssignDto dto) {
        return service.assign(id, dto);
    }

    // ---- DELETE ----
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }

    // ---- COLORS ----
    @GetMapping("/green")
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    public Page<TicketDto> listGreen(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return service.listGreen(page, size);
    }

    @GetMapping("/orange")
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    public Page<TicketDto> listOrange(@RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return service.listOrange(page, size);
    }

    @GetMapping("/red")
    @PreAuthorize("hasAnyRole('USER','TECHNICIAN','SUPERVISOR','ADMIN')")
    public Page<TicketDto> listRed(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "10") int size) {
        return service.listRed(page, size);
    }
}
