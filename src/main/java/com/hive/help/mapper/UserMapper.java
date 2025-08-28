package com.hive.help.mapper;

import com.hive.help.dto.user.UserCreateDto;
import com.hive.help.dto.user.UserDto;
import com.hive.help.dto.user.UserUpdateDto;
import com.hive.help.model.User;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "registrationDate", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "active", constant = "true")
    @Mapping(target = "createdTickets", ignore = true)
    @Mapping(target = "assignedTickets", ignore = true)

    User toEntity(UserCreateDto dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void update(@MappingTarget User user, UserUpdateDto dto);
}
