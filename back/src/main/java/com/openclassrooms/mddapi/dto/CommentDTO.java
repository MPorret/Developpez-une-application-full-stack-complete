package com.openclassrooms.mddapi.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CommentDTO {
    private String comment;
    private Integer post_id;
    private Integer author_id;
}
