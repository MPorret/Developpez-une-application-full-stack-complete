package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class MinimalUserDTO {
    private Integer id;
    private String name;
    private String email;
}
