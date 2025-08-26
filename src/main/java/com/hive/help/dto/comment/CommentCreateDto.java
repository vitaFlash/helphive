package com.hive.help.dto.comment;

import jakarta.validation.constraints.NotBlank;

public record CommentCreateDto(
        @NotBlank String body
) {}
