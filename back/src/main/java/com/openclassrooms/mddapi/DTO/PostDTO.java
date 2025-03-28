package com.openclassrooms.mddapi.DTO;

import java.util.List;

public class PostDTO {

  private Long id;
  private String title;
  private String content;
  private Long userId;
  private String authorName;
  private Long topicId;
  private List<CommentDTO> comments;
  private String createdAt;
  private String updatedAt;

  public PostDTO(Long id, String title, String content, Long userId, String authorName, Long topicId, List<CommentDTO> comments, String createdAt, String updatedAt) {
    this.id = id;
    this.title = title;
    this.content = content;
    this.userId = userId;
    this.authorName = authorName;
    this.topicId = topicId;
    this.comments = comments;
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

  public String getAuthorName() {
    return this.authorName;
  }

  public Long getTopicId() {
    return this.topicId;
  }

  public List<CommentDTO> getComments() {
    return this.comments;
  }

  public String getCreatedAt() {
    return this.createdAt;
  }

  public String getUpdatedAt() {
    return this.updatedAt;
  }
}
