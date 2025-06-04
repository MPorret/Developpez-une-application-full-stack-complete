package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.MinimalUserDTO;
import com.openclassrooms.mddapi.dto.RegisterDTO;
import com.openclassrooms.mddapi.dto.UserDTO;
import com.openclassrooms.mddapi.model.User;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Component
@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    User toEntity(RegisterDTO dto);

    @Mapping(target = "topics", ignore = true)
    UserDTO toDto(User user);

    MinimalUserDTO toMinimalDto(User user);
}
