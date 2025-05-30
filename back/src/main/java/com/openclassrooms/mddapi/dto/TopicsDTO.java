package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class TopicsDTO {
    private Integer id;
    private String name;
    private String description;
    private Boolean isSubscribed;
}
