package com.openclassrooms.mddapi.DTO;

public class CommentDTO {

  private final Long id;
  private final String content;
  private final String userName;
  private final Long userId;
  private final String createdAt;
  private final String updatedAt;

  public CommentDTO(Long id, String content, String userName, Long userId, String createdAt, String updatedAt) {
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

  public String getCreatedAt() {
    return this.createdAt;
  }

  public String getUpdatedAt() {
    return this.updatedAt;
  }
}
