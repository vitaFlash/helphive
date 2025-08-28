package com.hive.help.mapper;

import com.hive.help.dto.comment.CommentCreateDto;
import com.hive.help.dto.comment.CommentDto;
import com.hive.help.model.Comment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface CommentMapper {

    @Mappings({
            @Mapping(target = "authorId", source = "author.id"),
            @Mapping(target = "ticketId", source = "ticket.id")
    })
    CommentDto toDto(Comment comment);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "ticket", ignore = true),
            @Mapping(target = "author", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    })
    Comment toEntity(CommentCreateDto dto);
}

