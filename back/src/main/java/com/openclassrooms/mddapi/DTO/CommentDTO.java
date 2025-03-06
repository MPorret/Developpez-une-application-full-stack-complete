package com.openclassrooms.mddapi.DTO;

import java.time.LocalDateTime;

public class CommentDTO {

  private Long id;
  private String content;
  private String userName;
  private Long userId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public CommentDTO(Long id, String content, String userName, Long userId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.content = content;
    this.userName = userName;
    this.userId = userId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  // Getters

  public Long getId() {
    return this.id;
  }

  public String getContent() {
    return this.content;
  }

  public String getUserName() {
    return this.userName;
  }

  public Long getUserId() {
    return this.userId;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

}
