package com.hive.help.dto.ticket;

import com.hive.help.model.TicketStatus;
import jakarta.validation.constraints.NotNull;

public record TicketStatusUpdateDto(
        @NotNull TicketStatus status
) {}
