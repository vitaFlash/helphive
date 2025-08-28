package com.hive.help.mapper;

import com.hive.help.dto.ticket.*;
import com.hive.help.model.Ticket;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    @Mappings({
            @Mapping(target = "creatorId", source = "creator.id"),
            @Mapping(target = "technicianId", source = "assignedTechnician.id"),
            @Mapping(target = "color", expression = "java(ticket.getColor())")
    })

    TicketDto toDto(Ticket ticket);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "status", constant = "OPEN"),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())"),
            @Mapping(target = "updatedAt", ignore = true),
            @Mapping(target = "firstRespondedAt", ignore = true),
            @Mapping(target = "resolvedAt", ignore = true),
            @Mapping(target = "creator", ignore = true),
            @Mapping(target = "assignedTechnician", ignore = true),
            @Mapping(target = "comments", ignore = true),
            @Mapping(target = "workLogs", ignore = true)
    })

    Ticket toEntity(TicketCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget Ticket ticket, TicketUpdateDto dto);
}
