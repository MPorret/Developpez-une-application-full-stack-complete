package com.openclassrooms.mddapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.openclassrooms.mddapi.model.Topic;
import com.openclassrooms.mddapi.model.User;
import jakarta.websocket.MessageHandler;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
public class SubjectResponseDTO {
    private Integer id;
    @JsonProperty("name")
    private String title;
    @JsonProperty("description")
    private String message;
    private MinimalUserDTO author;
    private Topic topic;
    private List<MinimalCommentResponseDTO> comments;
    @JsonProperty("created_at")
    private Date createdAt;
    @JsonProperty("updated_at")
    private Date updatedAt;
}
