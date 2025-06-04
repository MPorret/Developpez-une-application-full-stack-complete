package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.SubjectDTO;
import com.openclassrooms.mddapi.dto.SubjectResponseDTO;
import com.openclassrooms.mddapi.model.Subject;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import org.springframework.stereotype.Component;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface SubjectMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Subject toEntity(SubjectDTO dto, User author, Topic topic);

    SubjectResponseDTO toDto(Subject subject);
    List<SubjectResponseDTO> toDto(List<Subject> subjects);
}
