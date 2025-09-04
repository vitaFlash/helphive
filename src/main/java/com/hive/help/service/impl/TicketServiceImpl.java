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
        Ticket ticket = mapper.toEntity(dto);
        ticket.setCreator(creator);
        return mapper.toDto(repo.save(ticket));
    }

    @Override
    public TicketDto get(Long id) {
        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));
        return mapper.toDto(ticket);
    }

    @Override
    public Page<TicketDto> listAll(int page, int size) {
        return repo.findAll(pageRequest(page, size))
                .map(mapper::toDto);
    }

    @Override
    public Page<TicketDto> listByCreator(Long creatorId, int page, int size) {
        return repo.findByCreatorId(creatorId, pageRequest(page, size))
                .map(mapper::toDto);
    }

    @Override
    public Page<TicketDto> listByAssignee(Long technicianId, int page, int size) {
        return repo.findByAssignedTechnicianId(technicianId, pageRequest(page, size))
                .map(mapper::toDto);
    }

    @Override
    public TicketDto update(Long id, TicketUpdateDto dto) {
        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));
        mapper.update(ticket, dto);
        ticket.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repo.save(ticket));
    }

    @Override
    public TicketDto updateStatus(Long id, TicketStatusUpdateDto dto) {
        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));

        TicketStatus previousStatus = ticket.getStatus();
        ticket.setStatus(dto.status());

        if (previousStatus == TicketStatus.OPEN &&
                dto.status() == TicketStatus.IN_PROGRESS &&
                ticket.getFirstRespondedAt() == null) {
            ticket.setFirstRespondedAt(LocalDateTime.now());
        }

        if (dto.status() == TicketStatus.RESOLVED &&
                ticket.getResolvedAt() == null) {
            ticket.setResolvedAt(LocalDateTime.now());
        }

        ticket.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repo.save(ticket));
    }

    @Override
    public TicketDto assign(Long id, TicketAssignDto dto) {
        Ticket ticket = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Ticket not found"));
        User technician = userRepo.findById(dto.technicianId())
                .orElseThrow(() -> new NotFoundException("Technician not found"));

        ticket.setAssignedTechnician(technician);
        ticket.setUpdatedAt(LocalDateTime.now());
        return mapper.toDto(repo.save(ticket));
    }

    @Override
    public void delete(Long id) {
        if (!repo.existsById(id)) {
            throw new NotFoundException("Ticket not found");
        }
        repo.deleteById(id);
    }

    @Override
    public Page<TicketDto> listGreen(int page, int size) {
        return repo.findGreen(pageRequest(page, size))
                .map(mapper::toDto);
    }

    @Override
    public Page<TicketDto> listOrange(int page, int size) {
        return repo.findOrange(pageRequest(page, size))
                .map(mapper::toDto);
    }

    @Override
    public Page<TicketDto> listRed(int page, int size) {
        return repo.findRed(pageRequest(page, size))
                .map(mapper::toDto);
    }

    private PageRequest pageRequest(int page, int size) {
        return PageRequest.of(page, size, Sort.by("createdAt").descending());
    }
}
