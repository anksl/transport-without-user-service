package com.transport.api.mapper;

import com.transport.api.dto.UserDto;
import com.transport.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User convert(UserDto userDto);

    UserDto convert(User user);
}