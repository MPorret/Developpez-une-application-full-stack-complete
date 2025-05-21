package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class TopicDTO {
    public String name;
    public String description;
}
