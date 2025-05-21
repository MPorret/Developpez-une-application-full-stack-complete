package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.model.Topic;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Component
@Mapper(componentModel = "spring")
public interface TopicMapper {
    TopicDTO toDto(Topic topic);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Topic toEntity(TopicDTO dto);
}
