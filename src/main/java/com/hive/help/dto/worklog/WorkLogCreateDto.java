package com.hive.help.dto.worklog;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record WorkLogCreateDto(
        @NotNull @Min(1) Integer minutes,
        String note
) {}