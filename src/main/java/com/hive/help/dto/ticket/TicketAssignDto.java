package com.hive.help.dto.ticket;

import jakarta.validation.constraints.NotNull;

public record TicketAssignDto(
        @NotNull Long technicianId
) {}
