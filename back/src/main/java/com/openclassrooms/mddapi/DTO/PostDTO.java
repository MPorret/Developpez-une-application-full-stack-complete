package com.openclassrooms.mddapi.DTO;

public class PostDTO {

  private Long id;
  private String title;
  private String content;
  private Long userId;
  private Long topicId;
  private String createdAt;
  private String updatedAt;

  public PostDTO(Long id, String title, String content, Long userId, Long topicId, String createdAt, String updatedAt) {
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

  public String getCreatedAt() {
    return this.createdAt;
  }

  public String getUpdatedAt() {
    return this.updatedAt;
  }

}
