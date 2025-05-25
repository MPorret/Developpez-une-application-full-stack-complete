package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class RegisterDTO {
    private String name;
    private String email;
    private String password;
}
