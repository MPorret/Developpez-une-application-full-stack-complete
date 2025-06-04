package com.openclassrooms.mddapi.dto;

import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import jakarta.websocket.MessageHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class SubjectResponseDTO {
    private Integer id;
    private String title;
    private String message;
    private MinimalUserDTO author;
    private Topic topic;
    private Date createdAt;
    private Date updatedAt;
}
