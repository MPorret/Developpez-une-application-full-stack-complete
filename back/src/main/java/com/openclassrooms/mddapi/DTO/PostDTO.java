package com.openclassrooms.mddapi.DTO;

import java.time.LocalDateTime;

public class PostDTO {

  private Long id;
  private String title;
  private String content;
  private Long userId;
  private Long topicId;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  public PostDTO(Long id, String title, String content, Long userId, Long topicId, LocalDateTime createdAt, LocalDateTime updatedAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.userId = userId;
    this.topicId = topicId;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public Long getId() {
    return this.id;
  }

  public String getTitle() {
    return this.title;
  }

  public String getContent() {
    return this.content;
  }

  public Long getUserId() {
    return this.userId;
  }

  public Long getTopicId() {
    return this.topicId;
  }

  public LocalDateTime getCreatedAt() {
    return this.createdAt;
  }

  public LocalDateTime getUpdatedAt() {
    return this.updatedAt;
  }

}
