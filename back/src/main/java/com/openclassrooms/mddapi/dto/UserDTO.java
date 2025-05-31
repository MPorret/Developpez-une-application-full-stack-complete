package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Topic;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {
    private Integer id;
    private String token;
    private String name;
    private String email;
    private Date createdAt;
    private Date updatedAt;
    private List<Topic> topics;
}
