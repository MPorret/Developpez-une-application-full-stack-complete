package com.openclassrooms.mddapi.DTO;

public class SubscriptionDTO {
  private Long id;
  private Long userId;
  private Long topicId;

  public SubscriptionDTO() {}

  public SubscriptionDTO(Long id, Long userId, Long topicId) {
      this.id = id;
      this.userId = userId;
      this.topicId = topicId;
  }

  public SubscriptionDTO(Long userId, Long topicId) {
    this.userId = userId;
    this.topicId = topicId;
  }

  public Long getId() {
    return this.id;
  }

  public Long getUserId() {
    return this.userId;
  }

  public Long getTopicId() {
    return this.topicId;
  }
}
