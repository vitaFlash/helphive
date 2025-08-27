package com.hive.help.service.impl;

import com.hive.help.dto.ticket.*;
import com.hive.help.exception.NotFoundException;
import com.hive.help.mapper.TicketMapper;
import com.hive.help.model.*;
import com.hive.help.repository.TicketRepository;
import com.hive.help.repository.UserRepository;
import com.hive.help.service.TicketService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class TicketServiceImpl implements TicketService {

    private final TicketRepository repo;
    private final UserRepository userRepo;
    private final TicketMapper mapper;

    @Override
    public TicketDto create(Long creatorId, TicketCreateDto dto) {
        User creator = userRepo.findById(creatorId)
                .orElseThrow(() -> new NotFoundException("Creator not found"));
        Ticket t = mapper.toEntity(dto);
        t.setCreator(creator);
        return mapper.toDto(repo.save(t));
    }

    @Override
    public TicketDto get(Long id) {
        return mapper.toDto(repo.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found")));
    }

    @Override
    public Page<TicketDto> listAll(int page, int size) {
        return repo.findAll(PageRequest.of(page, size, Sort.by("createdAt").descending())).map(mapper::toDto);
    }

    @Override
    public Page<TicketDto> listByCreator(Long creatorId, int page, int size) {
        return repo.findByCreatorId(creatorId, PageRequest.of(page, size, Sort.by("createdAt").descending()))
                .map(mapper::toDto);
    }

    @Override
    public Page<TicketDto> listByAssignee(Long technicianId, int page, int size) {
        return repo.findByAssignedTechnicianId(technicianId, PageRequest.of(page, size, Sort.by("createdAt").descending()))
                .map(mapper::toDto);
    }

    @Override
    public TicketDto update(Long id, TicketUpdateDto dto) {
        Ticket t = repo.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found"));
        mapper.update(t, dto);
        t.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repo.save(t));
    }

    @Override
    public TicketDto updateStatus(Long id, TicketStatusUpdateDto dto) {
        Ticket t = repo.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found"));
        TicketStatus from = t.getStatus();
        t.setStatus(dto.status());
        if (from == TicketStatus.OPEN && dto.status() == TicketStatus.IN_PROGRESS && t.getFirstRespondedAt() == null) {
            t.setFirstRespondedAt(LocalDateTime.now());
        }
        if (dto.status() == TicketStatus.RESOLVED && t.getResolvedAt() == null) {
            t.setResolvedAt(LocalDateTime.now());
        }
        t.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repo.save(t));
    }

    @Override
    public TicketDto assign(Long id, TicketAssignDto dto) {
        Ticket t = repo.findById(id).orElseThrow(() -> new NotFoundException("Ticket not found"));
        User tech = userRepo.findById(dto.technicianId())
                .orElseThrow(() -> new NotFoundException("Technician not found"));
        t.setAssignedTechnician(tech);
        t.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repo.save(t));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) throw new NotFoundException("Ticket not found");
        repo.deleteById(id);
    }

    @Override
    public Page<TicketDto> listGreen(int page, int size) {
        return repo.findGreen(PageRequest.of(page, size, Sort.by("createdAt").descending())).map(mapper::toDto);
    }

    @Override
    public Page<TicketDto> listOrange(int page, int size) {
        return repo.findOrange(PageRequest.of(page, size, Sort.by("createdAt").descending())).map(mapper::toDto);
    }

    @Override
    public Page<TicketDto> listRed(int page, int size) {
        return repo.findRed(PageRequest.of(page, size, Sort.by("createdAt").descending())).map(mapper::toDto);
    }
}