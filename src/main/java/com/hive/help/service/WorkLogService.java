package com.hive.help.service;

import com.hive.help.dto.worklog.*;
import org.springframework.data.domain.*;

public interface WorkLogService {
    WorkLogDto add(Long ticketId, Long technicianId, WorkLogCreateDto dto);
    Page<WorkLogDto> listByTicket(Long ticketId, int page, int size);
    Page<WorkLogDto> listByTechnician(Long technicianId, int page, int size);
}
