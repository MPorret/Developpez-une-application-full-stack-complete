package com.openclassrooms.mddapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="subscriptions")
public class Subscription {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)

  @Column(name = "id")
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "topic_id")
  private Long topicId;


  public Long getId() {
    return this.id;
  }

  public Long getUserId() {
    return this.userId;
  }

  public Long getTopicId() {
    return this.topicId;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setTopicId(Long topicId) {
    this.topicId = topicId;
  }

}
