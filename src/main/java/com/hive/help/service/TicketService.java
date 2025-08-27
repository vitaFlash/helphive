package com.hive.help.service;

import com.hive.help.dto.ticket.*;
import org.springframework.data.domain.*;

public interface TicketService {
    TicketDto create(Long creatorId, TicketCreateDto dto);
    TicketDto get(Long id);
    Page<TicketDto> listAll(int page, int size);
    Page<TicketDto> listByCreator(Long creatorId, int page, int size);
    Page<TicketDto> listByAssignee(Long technicianId, int page, int size);
    TicketDto update(Long id, TicketUpdateDto dto);
    TicketDto updateStatus(Long id, TicketStatusUpdateDto dto);
    TicketDto assign(Long id, TicketAssignDto dto);
    void delete(Long id);


    Page<TicketDto> listGreen(int page, int size);
    Page<TicketDto> listOrange(int page, int size);
    Page<TicketDto> listRed(int page, int size);
}