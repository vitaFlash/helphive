package com.hive.help.controller;

import com.hive.help.dto.ticket.*;
import com.hive.help.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tickets")
@RequiredArgsConstructor
public class TicketController {

    private final TicketService service;

    @PostMapping
    public TicketDto create(@RequestParam Long creatorId, @RequestBody TicketCreateDto dto) {
        return service.create(creatorId, dto);
    }

    @GetMapping("/{id}")
    public TicketDto get(@PathVariable Long id) { return service.get(id); }

    @GetMapping
    public Page<TicketDto> list(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "10") int size) {
        return service.listAll(page, size);
    }

    @GetMapping("/by-creator/{creatorId}")
    public Page<TicketDto> byCreator(@PathVariable Long creatorId,
                                     @RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "10") int size) {
        return service.listByCreator(creatorId, page, size);
    }

    @GetMapping("/by-assignee/{techId}")
    public Page<TicketDto> byAssignee(@PathVariable Long techId,
                                      @RequestParam(defaultValue = "0") int page,
                                      @RequestParam(defaultValue = "10") int size) {
        return service.listByAssignee(techId, page, size);
    }

    @PutMapping("/{id}")
    public TicketDto update(@PathVariable Long id, @RequestBody TicketUpdateDto dto) {
        return service.update(id, dto);
    }

    @PatchMapping("/{id}/status")
    public TicketDto updateStatus(@PathVariable Long id, @RequestBody TicketStatusUpdateDto dto) {
        return service.updateStatus(id, dto);
    }

    @PatchMapping("/{id}/assign")
    public TicketDto assign(@PathVariable Long id, @RequestBody TicketAssignDto dto) {
        return service.assign(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) { service.delete(id); }

    @GetMapping("/green")  public Page<TicketDto> green(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) { return service.listGreen(page, size); }
    @GetMapping("/orange") public Page<TicketDto> orange(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) { return service.listOrange(page, size); }
    @GetMapping("/red")    public Page<TicketDto> red(@RequestParam(defaultValue="0") int page, @RequestParam(defaultValue="10") int size) { return service.listRed(page, size); }
}
