package com.hive.help.dto.worklog;

import java.time.LocalDateTime;

public record WorkLogDto(
        Long id,
        Integer minutes,
        String note,
        Long ticketId,
        Long technicianId,
        LocalDateTime createdAt
) {}
