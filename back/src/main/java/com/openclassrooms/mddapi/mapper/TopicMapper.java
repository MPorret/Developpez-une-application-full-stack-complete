package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.TopicDTO;
import com.openclassrooms.mddapi.dto.TopicsDTO;
import com.openclassrooms.mddapi.model.Topic;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface TopicMapper {
    @Mapping(target = "isSubscribe", ignore = true)
    TopicsDTO toDto(Topic topic);
    @Mapping(target = "isSubscribe", ignore = true)
    List<TopicsDTO> toDtoList(List<Topic> listTopic);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Topic toEntity(TopicDTO dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Topic toEntity(TopicsDTO dto);
}
