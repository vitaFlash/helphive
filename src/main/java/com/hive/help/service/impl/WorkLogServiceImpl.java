package com.hive.help.service.impl;

import com.hive.help.dto.worklog.*;
import com.hive.help.exception.NotFoundException;
import com.hive.help.mapper.WorkLogMapper;
import com.hive.help.model.*;
import com.hive.help.repository.*;
import com.hive.help.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkLogServiceImpl implements WorkLogService {

    private final WorkLogRepository repo;
    private final TicketRepository ticketRepo;
    private final UserRepository userRepo;
    private final WorkLogMapper mapper;

    @Override
    public WorkLogDto add(Long ticketId, Long technicianId, WorkLogCreateDto dto) {
        Ticket t = ticketRepo.findById(ticketId).orElseThrow(() -> new NotFoundException("Ticket not found"));
        User tech = userRepo.findById(technicianId).orElseThrow(() -> new NotFoundException("Technician not found"));
        WorkLog wl = mapper.toEntity(dto);
        wl.setTicket(t);
        wl.setTechnician(tech);
        return mapper.toDto(repo.save(wl));
    }

    @Override
    public Page<WorkLogDto> listByTicket(Long ticketId, int page, int size) {
        return repo.findByTicketId(ticketId, PageRequest.of(page, size, Sort.by("createdAt").descending()))
                .map(mapper::toDto);
    }

    @Override
    public Page<WorkLogDto> listByTechnician(Long technicianId, int page, int size) {
        return repo.findByTechnicianId(technicianId, PageRequest.of(page, size, Sort.by("createdAt").descending()))
                .map(mapper::toDto);
    }
}
