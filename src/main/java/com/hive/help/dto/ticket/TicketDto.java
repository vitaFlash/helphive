package com.hive.help.dto.ticket;

import com.hive.help.model.TicketPriority;
import com.hive.help.model.TicketStatus;
import com.hive.help.model.TicketColor;
import java.time.LocalDateTime;

public record TicketDto(
        Long id,
        String title,
        String description,
        TicketStatus status,
        TicketPriority priority,
        TicketColor color,
        LocalDateTime createdAt,
        LocalDateTime updatedAt,
        LocalDateTime firstRespondedAt,
        LocalDateTime resolvedAt,
        Long creatorId,
        Long technicianId
) {}

