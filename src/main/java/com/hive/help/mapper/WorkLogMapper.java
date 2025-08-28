package com.hive.help.mapper;

import com.hive.help.dto.worklog.WorkLogCreateDto;
import com.hive.help.dto.worklog.WorkLogDto;
import com.hive.help.model.WorkLog;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface WorkLogMapper {

    @Mappings({
            @Mapping(target = "ticketId", source = "ticket.id"),
            @Mapping(target = "technicianId", source = "technician.id")
    })
    WorkLogDto toDto(WorkLog workLog);

    @Mappings({
            @Mapping(target = "id", ignore = true),
            @Mapping(target = "ticket", ignore = true),
            @Mapping(target = "technician", ignore = true),
            @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    })
    WorkLog toEntity(WorkLogCreateDto dto);
}
