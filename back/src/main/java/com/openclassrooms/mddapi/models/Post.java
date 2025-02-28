package com.openclassrooms.mddapi.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="posts")
public class Post {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "title")
  private String title;

  @Column(name = "content")
  private String content;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "topic_id")
  private Long topicId;

  @Column(name = "created_at")
  private String createdAt;

  @Column(name = "updated_at")
  private String updatedAt;


  // Getters

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


  // Setters

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public void setUserId(Long userId) {
    this.userId = userId;
  }

  public void setTopicId(Long topicId) {
    this.topicId = topicId;
  }

}
