package com.hive.help.dto.ticket;

import com.hive.help.model.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TicketCreateDto(
        @NotBlank String title,
        String description,
        @NotNull TicketPriority priority
) {}
