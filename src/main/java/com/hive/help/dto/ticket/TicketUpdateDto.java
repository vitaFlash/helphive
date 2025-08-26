package com.hive.help.dto.ticket;

import com.hive.help.model.TicketPriority;

public record TicketUpdateDto(
        String title,
        String description,
        TicketPriority priority
) {}