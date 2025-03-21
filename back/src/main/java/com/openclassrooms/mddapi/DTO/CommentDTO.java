package com.openclassrooms.mddapi.DTO;

public class CommentDTO {

  private Long id;
  private String content;
  private String userName;
  private Long userId;
  private String createdAt;
  private String updatedAt;

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
